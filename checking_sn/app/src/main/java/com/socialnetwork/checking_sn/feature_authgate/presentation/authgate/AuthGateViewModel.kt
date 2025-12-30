package com.socialnetwork.checking_sn.feature_authgate.presentation.authgate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthGateEvent {
    data class NavigateToFeed(val isLoggedIn: Boolean) : AuthGateEvent()
    object NavigateToAuth : AuthGateEvent()
}

@HiltViewModel
class AuthGateViewModel @Inject constructor(
    private val authManager: SecureTokenManager,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<AuthGateEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            // Check if we have a refresh token (persistent session indicator)
            val hasRefreshToken = !authManager.getRefreshToken().isNullOrEmpty()

            if (hasRefreshToken) {
                // We have a persistent session - attempt refresh token validation
                attemptTokenRefresh()
            } else {
                // No refresh token available, user needs to login
                delay(1500L) // Brief delay for smooth UX
                _eventFlow.emit(AuthGateEvent.NavigateToAuth)
            }
        }
    }

    private fun attemptTokenRefresh() {
        viewModelScope.launch {
            try {
                val result = authUseCases.refreshToken()

                when (result.result) {
                    is Resource.Success -> {
                        // Token refresh successful - user is authenticated
                        _eventFlow.emit(AuthGateEvent.NavigateToFeed(true))
                    }
                    is Resource.Error -> {
                        val error = result.result as Resource.Error

                        // Extract error message to determine if it's an explicit auth failure
                        val errorMessage = when (error.uiText) {
                            is com.socialnetwork.checking_sn.core.util.UiText.DynamicString -> error.uiText.value
                            is com.socialnetwork.checking_sn.core.util.UiText.StringResource -> "auth_error"
                            null -> "unknown_error"
                        }

                        // Only logout on explicit refresh token failures
                        val isExplicitAuthFailure = errorMessage.contains("Session expired") == true ||
                                errorMessage.contains("refresh token") == true ||
                                errorMessage.contains("invalid") == true ||
                                errorMessage.contains("unauthorized") == true

                        if (isExplicitAuthFailure) {
                            // Explicit refresh token failure - clear session
                            authManager.logout()
                            _eventFlow.emit(AuthGateEvent.NavigateToAuth)
                        } else {
                            // Network or temporary errors - assume session is still valid
                            // Allow user to proceed (TokenAuthenticator will handle refresh on API calls)
                            _eventFlow.emit(AuthGateEvent.NavigateToFeed(true))
                        }
                    }
                    null -> {
                        // Unexpected case - assume session valid for safety
                        _eventFlow.emit(AuthGateEvent.NavigateToFeed(true))
                    }
                }
            } catch (e: Exception) {
                // Network exceptions, timeouts, etc. - assume session still valid
                // Don't logout on connectivity issues
                _eventFlow.emit(AuthGateEvent.NavigateToFeed(true))
            }
        }
    }
}
