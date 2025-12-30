package com.socialnetwork.checking_sn.feature_auth.data.repository

import android.util.Log
import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRegisterResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.RefreshTokenRequest
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authManager: SecureTokenManager,
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(identifier: String, password: String): AuthResult {
        return try {
            // Determine if identifier is email or phone
            val isEmail = identifier.contains("@")
            val loginRequest = if (isEmail) {
                LoginRequest(email = identifier, password = password)
            } else {
                LoginRequest(phone = identifier, password = password)
            }

            val apiResponse = authApi.login(loginRequest)
            val response = apiResponse.body()
            if (response != null && response.success && apiResponse.isSuccessful) {
                // Store tokens securely
                authManager.saveAuthData(
                    accessToken = response.access ?: "",
                    refreshToken = response.refresh ?: "",
                    userId = response.user_id.toString()
                )
                AuthResult(result = Resource.Success(Unit))
            } else if (response != null && !response.success) {
                val error = response.error ?: "Login failed"
                when (error) {
                    "Account does not exist" -> {
                        // For account not found, show on the field that was used for login
                        if (isEmail) {
                            AuthResult(emailError = UiText.DynamicString(error))
                        } else {
                            AuthResult(phoneNumberError = UiText.DynamicString(error))
                        }
                    }
                    "Invalid email format" -> AuthResult(emailError = UiText.DynamicString(error))
                    "Incorrect password", "Account is deactivated" -> AuthResult(passwordError = UiText.DynamicString(error))
                    else -> {
                        // For unknown errors, show on the appropriate field
                        if (isEmail) {
                            AuthResult(emailError = UiText.DynamicString(error))
                        } else {
                            AuthResult(phoneNumberError = UiText.DynamicString(error))
                        }
                    }
                }
            } else {
                // Handle specific error codes with field errors if body not parsed
                val errorMessage = when (apiResponse.code()) {
                    400 -> "Bad request"
                    401 -> "Incorrect password"
                    404 -> "Account does not exist"
                    else -> "Network error: ${apiResponse.code()}"
                }
                if (apiResponse.code() in 400..499) {
                    // Map specific codes to field errors based on login type
                    when (apiResponse.code()) {
                        404 -> {
                            // Account not found - show on appropriate field
                            if (isEmail) {
                                AuthResult(emailError = UiText.DynamicString("Account does not exist"))
                            } else {
                                AuthResult(phoneNumberError = UiText.DynamicString("Account does not exist"))
                            }
                        }
                        401 -> AuthResult(passwordError = UiText.DynamicString("Incorrect password"))
                        else -> {
                            // Other client errors - show on appropriate field
                            if (isEmail) {
                                AuthResult(emailError = UiText.DynamicString("Invalid input"))
                            } else {
                                AuthResult(phoneNumberError = UiText.DynamicString("Invalid input"))
                            }
                        }
                    }
                } else {
                    AuthResult(result = Resource.Error(UiText.DynamicString(errorMessage)))
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.message}")))
        }
    }

    override suspend fun register(
        email: String?,
        phone: String?,
        name: String,
        password: String,
        password_confirm: String
    ): AuthResult {
        return try {
            val apiResponse = authApi.register(CreateAccountRequest(email?.trim(), phone?.trim(), name.trim(), password))
            val response = apiResponse.body()
            if (response != null && response.success && apiResponse.isSuccessful) {
                // Store tokens securely
                authManager.saveAuthData(
                    accessToken = response.access ?: "",
                    refreshToken = response.refresh ?: "",
                    userId = response.user_id.toString()
                )
                AuthResult(result = Resource.Success(Unit))
            } else if (response != null && !response.success) {
                val error = response.error ?: "Registration failed"
                when {
                    error.startsWith("Password must") -> AuthResult(passwordError = UiText.DynamicString(error))
                    error == "Invalid email format" || error == "Account already exists" || error == "Account with this email already exists" -> AuthResult(emailError = UiText.DynamicString(error))
                    error == "Account with this phone number already exists" -> AuthResult(phoneNumberError = UiText.DynamicString(error))
                    error == "Name is required" -> AuthResult(nameError = UiText.DynamicString(error))
                    else -> AuthResult(emailError = UiText.DynamicString(error))
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

    suspend fun checkEmailAvailable(email: String): Boolean {
        return try {
            val apiResponse = authApi.checkEmail(mapOf("email" to email.trim()))
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()
                response?.get("available") == true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Check email error", e)
            false
        }
    }

    suspend fun checkPhoneAvailable(phone: String): Boolean {
        return try {
            val apiResponse = authApi.checkPhone(mapOf("phone" to phone.trim()))
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()
                response?.get("available") == true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Check phone error", e)
            false
        }
    }

    override suspend fun refreshToken(): AuthResult {
        return try {
            // Get the refresh token
            val refreshToken = authManager.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                Log.w("AuthRepository", "No refresh token available for refresh")
                return AuthResult(result = Resource.Error(UiText.DynamicString("No refresh token available")))
            }

            val apiResponse = authApi.refreshToken(RefreshTokenRequest(refreshToken))
            val response = apiResponse.body()
            if (response != null && apiResponse.isSuccessful) {
                // Save new tokens (refresh token should be updated)
                authManager.saveAuthData(
                    accessToken = response.access ?: "",
                    refreshToken = response.refresh ?: refreshToken, // Use new refresh token or keep existing
                    userId = authManager.getUserId() ?: ""
                )
                AuthResult(result = Resource.Success(Unit))
        } else {
            Log.w("AuthRepository", "Token refresh failed with HTTP ${apiResponse.code()}")
            AuthResult(result = Resource.Error(UiText.DynamicString("Session expired. Please log in again.")))
        }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Refresh token error", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.message}")))
        }
    }

    override suspend fun logout(): AuthResult {
        return try {
            // Get the refresh token for blacklisting
            val refreshToken = authManager.getRefreshToken()
            if (refreshToken != null) {
                // Call backend to blacklist the refresh token
                val apiResponse = authApi.logout(RefreshTokenRequest(refreshToken))
                if (!apiResponse.isSuccessful) {
                    Log.w("AuthRepository", "Backend logout failed: ${apiResponse.code()}")
                    // Continue with local logout even if backend fails
                }
            }

            // Always clear local session regardless of backend response
            authManager.logout()
            AuthResult(result = Resource.Success(Unit))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Logout error", e)
            // Even if network fails, clear local session
            authManager.logout()
            AuthResult(result = Resource.Success(Unit))
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
