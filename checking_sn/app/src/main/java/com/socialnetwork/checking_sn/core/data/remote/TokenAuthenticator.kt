package com.socialnetwork.checking_sn.core.data.remote

import android.util.Log
import com.google.gson.Gson
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.util.EnvironmentConfig
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val secureTokenManager: SecureTokenManager
) : Authenticator {

    companion object {
        private const val TAG = "TokenAuthenticator"
        private var isRefreshing = false
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val originalRequest = response.request

        // Don't retry refresh requests themselves to avoid loops
        if (originalRequest.url.toString().contains("/api/auth/refresh/")) {
            Log.w(TAG, "Refresh token request failed, clearing session")
            secureTokenManager.logout()
            return null // Don't retry, let the app handle logout
        }

        // Only retry once per request chain
        val retryCount = originalRequest.header("X-Retry-Count")?.toIntOrNull() ?: 0
        if (retryCount >= 1) {
            Log.w(TAG, "Max retry count reached, clearing session")
            secureTokenManager.logout()
            return null
        }

        // Get refresh token first
        val refreshToken = secureTokenManager.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) {
            Log.w(TAG, "No refresh token available, clearing session")
            secureTokenManager.logout()
            return null
        }

        // Synchronize refresh to avoid multiple concurrent refreshes
        synchronized(this) {
            if (isRefreshing) {
                Log.d(TAG, "Refresh already in progress, waiting...")
                return null // Let the ongoing refresh complete
            }

            isRefreshing = true
        }

        return try {
            runBlocking {
                Log.d(TAG, "Attempting token refresh")

                // Create refresh request manually to avoid circular dependency
                val gson = Gson()
                val refreshRequestBody = gson.toJson(RefreshTokenRequest(refreshToken))
                    .toRequestBody("application/json".toMediaType())

                val refreshHttpRequest = Request.Builder()
                    .url(EnvironmentConfig.baseUrl + "api/auth/refresh/")
                    .post(refreshRequestBody)
                    .build()

                val refreshResponse = okHttpClient.newCall(refreshHttpRequest).execute()

                if (refreshResponse.isSuccessful) {
                    val responseBody = refreshResponse.body?.string()
                    if (responseBody != null) {
                        val newTokens = gson.fromJson(responseBody, AuthResponse::class.java)
                        if (newTokens.access.isNotEmpty() && newTokens.refresh.isNotEmpty()) {
                            // Save new tokens (use the new refresh token from response)
                            secureTokenManager.saveAuthData(
                                accessToken = newTokens.access,
                                refreshToken = newTokens.refresh,
                                userId = secureTokenManager.getUserId() ?: ""
                            )

                            Log.d(TAG, "Token refresh successful, retrying original request")

                            // Retry the original request with new access token
                            return@runBlocking originalRequest.newBuilder()
                                .header("Authorization", "Bearer ${newTokens.access}")
                                .header("X-Retry-Count", (retryCount + 1).toString())
                                .build()
                        }
                    }
                }

                Log.w(TAG, "Token refresh failed, clearing session")
                secureTokenManager.logout()
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Token refresh error", e)
            secureTokenManager.logout()
            null
        } finally {
            synchronized(this) {
                isRefreshing = false
            }
        }
    }
}
