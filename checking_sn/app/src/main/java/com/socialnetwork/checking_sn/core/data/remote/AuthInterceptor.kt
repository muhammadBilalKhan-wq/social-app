package com.socialnetwork.checking_sn.core.data.remote

import android.util.Log
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor that adds Authorization header to requests that require authentication.
 * - Only adds Bearer token when available
 * - Skips auth endpoints (login, register, refresh, check endpoints)
 * - Works seamlessly with token refresh logic
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val secureTokenManager: SecureTokenManager
) : Interceptor {

    companion object {
        private const val TAG = "AuthInterceptor"
        private val AUTH_ENDPOINTS = setOf(
            "/api/auth/login/",
            "/api/auth/register/",
            "/api/auth/refresh/",
            "/api/auth/check_email/",
            "/api/auth/check_phone/"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.toString()

        // Skip adding auth header to authentication endpoints
        if (isAuthEndpoint(url)) {
            Log.d(TAG, "Skipping auth header for endpoint: $url")
            return chain.proceed(originalRequest)
        }

        // Get access token
        val accessToken = secureTokenManager.getAccessToken()

        // Only add header if token is available
        val newRequest = if (!accessToken.isNullOrEmpty()) {
            Log.d(TAG, "Adding auth header for: $url")
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            Log.d(TAG, "No access token available for: $url")
            originalRequest
        }

        return chain.proceed(newRequest)
    }

    private fun isAuthEndpoint(url: String): Boolean {
        return AUTH_ENDPOINTS.any { endpoint ->
            url.contains(endpoint)
        }
    }
}
