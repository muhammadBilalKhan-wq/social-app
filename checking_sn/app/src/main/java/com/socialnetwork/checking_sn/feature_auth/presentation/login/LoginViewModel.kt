package com.socialnetwork.checking_sn.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import com.socialnetwork.checking_sn.feature_auth.presentation.login.components.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _emailState.value = emailState.value.copy(error = null)
            _loginState.value = LoginState(isLoading = true)
            val loginResult = authUseCases.login(
                email = emailState.value.text
            )
            if(loginResult.emailError != null) {
                _emailState.value = emailState.value.copy(
                    error = loginResult.emailError
                )
            }
            when(loginResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.OnLogin
                    )
                }
                is Resource.Error -> {
                    _loginState.value = LoginState(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(loginResult.result.uiText ?: UiText.DynamicString(""))
                    )
                }

                null -> {
                    _loginState.value = LoginState(isLoading = false)
                }
            }
        }
    }
}
