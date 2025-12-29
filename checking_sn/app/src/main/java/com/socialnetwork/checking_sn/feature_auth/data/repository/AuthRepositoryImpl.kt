package com.socialnetwork.checking_sn.feature_auth.data.repository

import android.util.Log
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        // Backend removed: Simulate successful login for demo purposes
        Log.d("AuthRepository", "Simulating login for email: $email (backend removed)")
        // Mock auth response
        val mockAuthResponse = com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse(
            access = "mock_access_token",
            refresh = "mock_refresh_token",
            userId = "mock_user_id"
        )
        authManager.saveAuthData(mockAuthResponse)
        return AuthResult(result = Resource.Success(Unit))
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String,
        password_confirm: String
    ): AuthResult {
        // Backend removed: Simulate successful registration for demo purposes
        Log.d("AuthRepository", "Simulating registration for email: $email, username: $username (backend removed)")
        return AuthResult(result = Resource.Success(Unit))
    }

    override suspend fun getMe(): AuthResult {
        // Backend removed: Assume user is authenticated if token exists
        val hasToken = authManager.getAccessToken() != null
        return if (hasToken) {
            AuthResult(result = Resource.Success(Unit))
        } else {
            authManager.logout()
            AuthResult(result = Resource.Error(UiText.DynamicString("Session expired. Please log in again.")))
        }
    }
}
