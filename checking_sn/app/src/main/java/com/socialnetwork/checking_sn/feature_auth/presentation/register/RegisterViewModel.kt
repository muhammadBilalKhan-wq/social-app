package com.socialnetwork.checking_sn.feature_auth.presentation.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import com.socialnetwork.checking_sn.feature_auth.presentation.login.components.PasswordTextFieldState
import com.socialnetwork.checking_sn.feature_auth.presentation.login.components.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _passwordConfirmState = mutableStateOf(PasswordTextFieldState())
    val passwordConfirmState: State<PasswordTextFieldState> = _passwordConfirmState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPasswordConfirm -> {
                _passwordConfirmState.value = passwordConfirmState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        Log.d("RegisterViewModel", "register() called")
        viewModelScope.launch {
            Log.d("RegisterViewModel", "Coroutine started - launching API call")
            _emailState.value = emailState.value.copy(error = null)
            _usernameState.value = usernameState.value.copy(error = null)
            _passwordState.value = passwordState.value.copy(error = null)
            _passwordConfirmState.value = passwordConfirmState.value.copy(error = null)
            _registerState.value = RegisterState(isLoading = true)
            val registerResult = authUseCases.register(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text,
                password_confirm = passwordConfirmState.value.text
            )
            if(registerResult.emailError != null) {
                _emailState.value = emailState.value.copy(
                    error = registerResult.emailError
                )
            }
            if(registerResult.usernameError != null) {
                _usernameState.value = usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }
            if(registerResult.passwordError != null) {
                _passwordState.value = passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }
            if(registerResult.passwordConfirmError != null) {
                _passwordConfirmState.value = passwordConfirmState.value.copy(
                    error = registerResult.passwordConfirmError
                )
            }
            when(registerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.OnRegister
                    )
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(registerResult.result.uiText ?: UiText.DynamicString(""))
                    )
                }
                 null -> {
                    _registerState.value = RegisterState(isLoading = false)
                 }
            }
        }
    }
}
