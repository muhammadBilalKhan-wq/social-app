package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
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
    data class NavigateToAuth(val isLoggedIn: Boolean) : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authManager: AuthManager,
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
            val hasToken = authManager.getAccessToken() != null
            if (hasToken) {
                // Verify token via /auth/me
                val result = authUseCases.getMe()
                when (result.result) {
                    is Resource.Success -> {
                        _eventFlow.emit(SplashEvent.NavigateToAuth(true))
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(SplashEvent.NavigateToAuth(false))
                    }
                    null -> {
                        _eventFlow.emit(SplashEvent.NavigateToAuth(false))
                    }
                }
            } else {
                _eventFlow.emit(SplashEvent.NavigateToAuth(false))
            }
        }
    }
}
