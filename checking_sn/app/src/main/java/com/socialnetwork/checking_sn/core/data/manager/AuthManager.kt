package com.socialnetwork.checking_sn.core.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.socialnetwork.checking_sn.core.util.Constants
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val ACCESS_TOKEN_KEY = "jwt_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val USER_ID_KEY = "user_id"
    }

    private val _isLoggedIn = MutableStateFlow(prefs.getString(ACCESS_TOKEN_KEY, null) != null)
    val isLoggedIn: Flow<Boolean> = _isLoggedIn.asStateFlow()

    fun saveAuthData(authResponse: AuthResponse) {
        prefs.edit()
            .putString(ACCESS_TOKEN_KEY, authResponse.access)
            .putString(REFRESH_TOKEN_KEY, authResponse.refresh)
            .putString(USER_ID_KEY, authResponse.userId)
            .apply()
        _isLoggedIn.value = true
    }

    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun logout() {
        prefs.edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .remove(USER_ID_KEY)
            .apply()
        _isLoggedIn.value = false
    }
}
