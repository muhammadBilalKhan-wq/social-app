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
                val isValid = authUseCases.validateEmail(event.value) == null
                _uiState.update {
                    it.copy(
                        email = event.value,
                        isPasswordVisible = isValid && event.value.isNotBlank()
                    )
                }
            }
            is LoginEvent.EnteredPhoneNumber -> {
                val isValid = authUseCases.validatePhoneNumber(event.value, uiState.value.countryIsoCode) == null
                _uiState.update {
                    it.copy(
                        phoneNumber = event.value,
                        isPasswordVisible = isValid && event.value.isNotBlank()
                    )
                }
            }
            is LoginEvent.EnteredPassword -> {
                _uiState.update { it.copy(password = event.value, passwordError = null) }
            }
            is LoginEvent.SelectedOption -> {
                _uiState.update { it.copy(selectedOption = event.option, emailError = null, phoneNumberError = null, isPasswordVisible = false) }
            }
            is LoginEvent.SelectedCountry -> {
                _uiState.update { it.copy(countryCode = event.code, countryIsoCode = event.isoCode) }
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        val currentState = uiState.value

        // Validate password locally first with basic validation for login
        val passwordError = authUseCases.validateLoginPassword(currentState.password)
        if (passwordError != null) {
            _uiState.update { it.copy(passwordError = passwordError) }
            return
        }

        // Clear any previous password error
        _uiState.update { it.copy(passwordError = null) }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, emailError = null, phoneNumberError = null) }

            // Validate input based on selected option
            val validationError = when (currentState.selectedOption) {
                "Email" -> {
                    val emailError = authUseCases.validateEmail(currentState.email)
                    if (emailError != null) {
                        _uiState.update { it.copy(emailError = emailError, isLoading = false) }
                        return@launch
                    }
                    null
                }
                "Phone" -> {
                    val phoneError = authUseCases.validatePhoneNumber(
                        currentState.phoneNumber,
                        currentState.countryIsoCode
                    )
                    if (phoneError != null) {
                        _uiState.update { it.copy(phoneNumberError = phoneError, isLoading = false) }
                        return@launch
                    }
                    null
                }
                else -> null
            }

            if (validationError != null) return@launch

            val identifier = when (currentState.selectedOption) {
                "Email" -> currentState.email
                "Phone" -> currentState.countryCode + currentState.phoneNumber
                else -> currentState.email
            }

            val loginResult = authUseCases.login(identifier = identifier, password = currentState.password)

            _uiState.update { it.copy(
                emailError = loginResult.emailError,
                phoneNumberError = loginResult.phoneNumberError,
                passwordError = loginResult.passwordError,
                isLoading = false
            ) }
            when(loginResult.result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(emailError = null, phoneNumberError = null, passwordError = null) }
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
