package com.socialnetwork.checking_sn.feature_auth.data.repository

import android.util.Log
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        Log.d("AuthRepository", "Attempting login for email: $email")
        return try {
            val response = api.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
            Log.d("AuthRepository", "Login response: ${response.successful}, message: ${response.message}")
            if (response.successful) {
                response.data?.let { authResponse ->
                    authManager.saveAuthData(authResponse)
                }
                AuthResult(result = Resource.Success(Unit))
            } else {
                response.message?.let {
                    AuthResult(result = Resource.Error(UiText.DynamicString(it)))
                } ?: AuthResult(result = Resource.Error(UiText.DynamicString("An unknown error occurred")))
            }
        } catch (e: IOException) {
            Log.e("AuthRepository", "Network error during login", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: Couldn't reach the server. Check your connection.")))
        } catch (e: HttpException) {
            Log.e("AuthRepository", "HTTP error during login: ${e.code()}", e)
            try {
                val errorBody = e.response()?.errorBody()?.string()
                Log.d("AuthRepository", "Login error response body: $errorBody")
                if (errorBody != null) {
                    // Parse JSON response
                    val gson = com.google.gson.Gson()
                    val errorResponse = gson.fromJson(errorBody, com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse::class.java)
                    val backendMessage = errorResponse.message
                    if (backendMessage != null) {
                        AuthResult(result = Resource.Error(UiText.DynamicString(backendMessage)))
                    } else {
                        AuthResult(result = Resource.Error(UiText.DynamicString("Login failed")))
                    }
                } else {
                    AuthResult(result = Resource.Error(UiText.DynamicString("Login failed")))
                }
            } catch (parseException: Exception) {
                Log.e("AuthRepository", "Failed to parse login error response", parseException)
                AuthResult(result = Resource.Error(UiText.DynamicString("Login failed")))
            }
        }
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String,
        password_confirm: String
    ): AuthResult {
        val fullUrl = "${AuthApi.BASE_URL}api/auth/register"
        Log.d("AuthRepository", "Attempting registration - Full URL: $fullUrl")
        Log.d("AuthRepository", "Registration payload: email=$email, username=$username")
        return try {
            val response = api.register(
                CreateAccountRequest(
                    email = email,
                    username = username,
                    password = password,
                    password_confirm = password_confirm
                )
            )
            Log.d("AuthRepository", "Registration response: successful=${response.successful}, message=${response.message}")
            if (response.successful) {
                AuthResult(result = Resource.Success(Unit))
            } else {
                response.message?.let {
                    AuthResult(result = Resource.Error(UiText.DynamicString(it)))
                } ?: AuthResult(result = Resource.Error(UiText.DynamicString("An unknown error occurred")))
            }
        } catch (e: IOException) {
            Log.e("AuthRepository", "Network error during registration - URL: $fullUrl, Exception: ${e.javaClass.simpleName}: ${e.message}", e)
            AuthResult(result = Resource.Error(UiText.DynamicString("Network error: ${e.javaClass.simpleName} - ${e.message ?: "Unknown network issue"}")))
        } catch (e: HttpException) {
            Log.e("AuthRepository", "HTTP error during registration - URL: $fullUrl, Code: ${e.code()}", e)
            try {
                val errorBody = e.response()?.errorBody()?.string()
                Log.d("AuthRepository", "Error response body: $errorBody")
                if (errorBody != null) {
                    // Parse JSON response
                    val gson = com.google.gson.Gson()
                    val errorResponse = gson.fromJson(errorBody, com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse::class.java)
                    val backendMessage = errorResponse.message
                    if (backendMessage != null) {
                        AuthResult(result = Resource.Error(UiText.DynamicString(backendMessage)))
                    } else {
                        AuthResult(result = Resource.Error(UiText.DynamicString("Registration failed")))
                    }
                } else {
                    AuthResult(result = Resource.Error(UiText.DynamicString("Registration failed")))
                }
            } catch (parseException: Exception) {
                Log.e("AuthRepository", "Failed to parse error response", parseException)
                AuthResult(result = Resource.Error(UiText.DynamicString("Registration failed")))
            }
        }
    }

    override suspend fun getMe(): AuthResult {
        return try {
            val response = api.getMe()
            if (response.successful) {
                AuthResult(result = Resource.Success(Unit))
            } else {
                authManager.logout()
                response.message?.let {
                    AuthResult(result = Resource.Error(UiText.DynamicString(it)))
                } ?: AuthResult(result = Resource.Error(UiText.DynamicString("An unknown error occurred")))
            }
        } catch (e: IOException) {
            authManager.logout()
            AuthResult(result = Resource.Error(UiText.DynamicString("Couldn't reach server, please check your internet connection.")))
        } catch (e: HttpException) {
            authManager.logout()
            AuthResult(result = Resource.Error(UiText.DynamicString("Oops, something went wrong!")))
        }
    }
}
