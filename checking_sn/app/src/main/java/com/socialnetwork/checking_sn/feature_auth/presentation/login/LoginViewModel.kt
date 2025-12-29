package com.socialnetwork.checking_sn.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _uiState.update { it.copy(email = event.value, emailError = null) }
            }
            is LoginEvent.EnteredPassword -> {
                _uiState.update { it.copy(password = event.value, passwordError = null) }
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, emailError = null, passwordError = null) }
            val loginResult = authUseCases.login(
                email = uiState.value.email,
                password = uiState.value.password
            )
            _uiState.update { it.copy(
                emailError = loginResult.emailError,
                passwordError = loginResult.passwordError,
                isLoading = false
            ) }
            when(loginResult.result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(emailError = null, passwordError = null) }
                    _eventFlow.emit(UiEvent.OnLogin)
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(loginResult.result.uiText ?: UiText.DynamicString(""))
                    )
                }
                null -> {}
            }
        }
    }
}
