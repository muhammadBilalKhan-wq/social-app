package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashEvent {
    data class NavigateToHome(val isLoggedIn: Boolean) : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authManager: SecureTokenManager,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            delay(4000L) // Match splash delay

            // Check if we have a refresh token (persistent session indicator)
            val hasRefreshToken = !authManager.getRefreshToken().isNullOrEmpty()

            if (hasRefreshToken) {
                // We have a persistent session - assume user is logged in
                // Don't validate tokens during splash to avoid premature logout
                // TokenAuthenticator will handle refresh on first API call if needed
                _eventFlow.emit(SplashEvent.NavigateToHome(true))

                // Optional: Background validation (don't block UI)
                // This can help detect obviously invalid sessions without blocking
                validateSessionInBackground()
            } else {
                // No refresh token available, user needs to login
                _eventFlow.emit(SplashEvent.NavigateToHome(false))
            }
        }
    }

    /**
     * Optional background validation that doesn't affect splash flow
     * Only logs out if we're absolutely sure refresh failed
     */
    private fun validateSessionInBackground() {
        viewModelScope.launch {
            try {
                delay(1000L) // Small delay to not interfere with splash

                val result = authUseCases.getMe()

                when (result.result) {
                    is Resource.Success -> {
                        // Session validated successfully - no action needed
                    }
                    is Resource.Error -> {
                        val error = result.result as Resource.Error

                        // Extract error message from UiText
                        val errorMessage = when (error.uiText) {
                            is com.socialnetwork.checking_sn.core.util.UiText.DynamicString -> error.uiText.value
                            is com.socialnetwork.checking_sn.core.util.UiText.StringResource -> {
                                // For string resources, we can't resolve without context, so assume auth error for safety
                                // This is rare in this context as auth errors are usually dynamic strings
                                "auth_error"
                            }
                            null -> "unknown_error"
                        }

                        // Only logout if it's clearly an auth error AND refresh failed
                        val isAuthError = errorMessage.contains("401") == true ||
                                        errorMessage.contains("403") == true ||
                                        errorMessage.contains("Unauthorized") == true ||
                                        errorMessage.contains("Forbidden") == true ||
                                        errorMessage.contains("Session expired") == true ||
                                        errorMessage.contains("Authentication failed") == true

                        if (isAuthError) {
                            // Double-check: Make sure refresh token still exists
                            // If TokenAuthenticator successfully refreshed, we wouldn't be here
                            val refreshTokenStillExists = !authManager.getRefreshToken().isNullOrEmpty()

                            if (!refreshTokenStillExists) {
                                // Refresh token is gone - session is definitely invalid
                                authManager.logout()
                                // Note: Navigation is handled by reactive auth state observers
                            }
                            // If refresh token still exists, assume TokenAuthenticator will handle it
                        }
                        // Non-auth errors (network, server) - leave session alone
                    }
                    null -> {
                        // Unexpected case - leave session alone for safety
                    }
                }
            } catch (e: Exception) {
                // Network exceptions during background validation - leave session alone
            }
        }
    }
}
