package com.socialnetwork.checking_sn.core.data.manager

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureTokenManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val TAG = "SecureTokenManager"
        private const val PREFS_NAME = "secure_auth_prefs"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val USER_ID_KEY = "user_id"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _isLoggedIn = MutableStateFlow(getAccessToken() != null)
    val isLoggedIn: Flow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        // Log initialization and check persistence
        Log.d(TAG, "SecureTokenManager initialized")
        auditTokenPersistence()
    }

    /**
     * Audits token persistence across app lifecycle events
     * Logs current state without exposing sensitive data
     */
    private fun auditTokenPersistence() {
        val hasAccessToken = getAccessToken()?.isNotEmpty() == true
        val hasRefreshToken = getRefreshToken()?.isNotEmpty() == true
        val hasUserId = getUserId()?.isNotEmpty() == true

        Log.d(TAG, "Token persistence audit - Access: $hasAccessToken, Refresh: $hasRefreshToken, UserId: $hasUserId")

        // Defensive check: ensure all tokens are present or all are absent
        val tokenState = when {
            hasAccessToken && hasRefreshToken && hasUserId -> "COMPLETE_SESSION"
            !hasAccessToken && !hasRefreshToken && !hasUserId -> "NO_SESSION"
            else -> "INCONSISTENT_SESSION"
        }

        when (tokenState) {
            "COMPLETE_SESSION" -> Log.d(TAG, "Session state: Valid complete session detected")
            "NO_SESSION" -> Log.d(TAG, "Session state: No active session")
            "INCONSISTENT_SESSION" -> {
                Log.w(TAG, "Session state: Inconsistent token state detected - clearing for safety")
                // Auto-correct inconsistent state by clearing all tokens
                clearAllTokensSafely()
            }
        }
    }

    fun saveAuthData(accessToken: String, refreshToken: String, userId: String) {
        // Safety checks
        require(accessToken.isNotBlank()) { "Access token cannot be null or blank" }
        require(refreshToken.isNotBlank()) { "Refresh token cannot be null or blank" }
        require(userId.isNotBlank()) { "User ID cannot be null or blank" }

        Log.d(TAG, "Saving auth data - Access token: ${accessToken.take(10)}..., Refresh token: ${refreshToken.take(10)}..., User ID: $userId")

        encryptedPrefs.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .putString(USER_ID_KEY, userId)
            .apply()

        _isLoggedIn.value = true
        Log.d(TAG, "Auth data saved successfully")
    }

    fun getAccessToken(): String? {
        val token = encryptedPrefs.getString(ACCESS_TOKEN_KEY, null)
        Log.d(TAG, "Retrieved access token: ${token?.take(10)}${if (token != null) "..." else "null"}")
        return token
    }

    fun getRefreshToken(): String? {
        val token = encryptedPrefs.getString(REFRESH_TOKEN_KEY, null)
        Log.d(TAG, "Retrieved refresh token: ${token?.take(10)}${if (token != null) "..." else "null"}")
        return token
    }

    fun getUserId(): String? {
        val userId = encryptedPrefs.getString(USER_ID_KEY, null)
        Log.d(TAG, "Retrieved user ID: $userId")
        return userId
    }

    /**
     * Safely clears all tokens without triggering logout flow
     * Used for correcting inconsistent token states
     */
    private fun clearAllTokensSafely() {
        Log.d(TAG, "Safely clearing inconsistent tokens")

        try {
            encryptedPrefs.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .remove(USER_ID_KEY)
                .apply()

            _isLoggedIn.value = false
            Log.d(TAG, "Inconsistent tokens cleared successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing inconsistent tokens", e)
        }
    }

    fun logout() {
        Log.d(TAG, "Performing logout - clearing all stored tokens")

        val hadTokens = getAccessToken() != null || getRefreshToken() != null || getUserId() != null
        Log.d(TAG, "Tokens present before logout: $hadTokens")

        encryptedPrefs.edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .remove(USER_ID_KEY)
            .apply()

        _isLoggedIn.value = false
        Log.d(TAG, "Logout completed - all tokens cleared")
    }
}
