package com.socialnetwork.checking_sn.feature_auth.data.repository

import android.util.Log
import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRegisterResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authManager: AuthManager,
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val apiResponse = authApi.login(LoginRequest(email, password))
            val response = apiResponse.body()
            if (response != null && response.success && apiResponse.isSuccessful) {
                // Now using real JWT tokens from backend
                val authResponse = com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse(
                    access = response.access ?: "",
                    refresh = response.refresh ?: "",
                    userId = response.user_id.toString()
                )
                authManager.saveAuthData(authResponse)
                AuthResult(result = Resource.Success(Unit))
            } else if (response != null && !response.success) {
                val error = response.error ?: "Login failed"
                when (error) {
                    "Account does not exist", "Invalid email format" -> AuthResult(emailError = UiText.DynamicString(error))
                    "Incorrect password" -> AuthResult(passwordError = UiText.DynamicString(error))
                    else -> AuthResult(result = Resource.Error(UiText.DynamicString(error)))
                }
            } else {
                // Handle HTTP error codes when no body or parsing failed
                val errorMessage = when (apiResponse.code()) {
                    400 -> "Bad request"
                    401 -> "Incorrect password"
                    404 -> "Account does not exist"
                    else -> "Network error: ${apiResponse.code()}"
                }
                AuthResult(result = Resource.Error(UiText.DynamicString(errorMessage)))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.message}")))
        }
    }

    override suspend fun register(
        email: String,
        name: String,
        password: String,
        password_confirm: String
    ): AuthResult {
        return try {
            val apiResponse = authApi.register(CreateAccountRequest(email, name, password))
            val response = apiResponse.body()
            if (response != null && response.success && apiResponse.isSuccessful) {
                // Now using real JWT tokens from backend
                val authResponse = com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse(
                    access = response.access ?: "",
                    refresh = response.refresh ?: "",
                    userId = response.user_id.toString()
                )
                authManager.saveAuthData(authResponse)
                AuthResult(result = Resource.Success(Unit))
            } else if (response != null && !response.success) {
                val error = response.error ?: "Registration failed"
                when (error) {
                    "Invalid email format" -> AuthResult(emailError = UiText.DynamicString(error))
                    else -> AuthResult(result = Resource.Error(UiText.DynamicString(error)))
                }
            } else {
                val errorMessage = when (apiResponse.code()) {
                    400 -> "Bad request"
                    else -> "Network error: ${apiResponse.code()}"
                }
                AuthResult(result = Resource.Error(UiText.DynamicString(errorMessage)))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Register error", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.message}")))
        }
    }

    override suspend fun getMe(): AuthResult {
        return try {
            val apiResponse = authApi.getMe()
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()
                if (response != null && response.successful) {
                    AuthResult(result = Resource.Success(Unit))
                } else {
                    authManager.logout()
                    AuthResult(result = Resource.Error(UiText.DynamicString("Authentication failed")))
                }
            } else {
                authManager.logout()
                AuthResult(result = Resource.Error(UiText.DynamicString("Session expired. Please log in again.")))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "GetMe error", e)
            authManager.logout()
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.message}")))
        }
    }
}
