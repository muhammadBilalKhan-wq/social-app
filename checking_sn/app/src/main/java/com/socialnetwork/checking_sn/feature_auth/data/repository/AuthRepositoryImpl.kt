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
                    "Incorrect password", "Account is deactivated" -> AuthResult(passwordError = UiText.DynamicString(error))
                    else -> AuthResult(emailError = UiText.DynamicString(error))
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
                    // Map specific codes to field errors
                    when (apiResponse.code()) {
                        404 -> AuthResult(emailError = UiText.DynamicString("Account does not exist"))
                        401 -> AuthResult(passwordError = UiText.DynamicString("Incorrect password"))
                        else -> AuthResult(emailError = UiText.DynamicString("Invalid input"))
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
                    "Invalid email format", "Account already exists", "Account with this email already exists" -> AuthResult(emailError = UiText.DynamicString(error))
                    "Account with this phone number already exists" -> AuthResult(phoneNumberError = UiText.DynamicString(error))
                    "Password too short" -> AuthResult(passwordError = UiText.DynamicString(error))
                    "Name is required" -> AuthResult(nameError = UiText.DynamicString(error))
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
