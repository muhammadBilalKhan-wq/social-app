package com.socialnetwork.checking_sn.feature_auth.presentation.register

import android.util.Log
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
class RegisterViewModel @Inject constructor(
    val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredEmail -> {
                _uiState.update { it.copy(email = event.value, emailError = null) }
            }
            is RegisterEvent.EnteredPhoneNumber -> {
                _uiState.update { it.copy(phoneNumber = event.value, phoneNumberError = null) }
            }
            is RegisterEvent.EnteredName -> {
                _uiState.update { it.copy(name = event.value, nameError = null) }
            }
            is RegisterEvent.EnteredPassword -> {
                _uiState.update { it.copy(password = event.value, passwordError = null) }
            }
            is RegisterEvent.EnteredPasswordConfirm -> {
                // Handle in details screen
            }
            is RegisterEvent.SelectedOption -> {
                _uiState.update { it.copy(selectedOption = event.option, emailError = null, phoneNumberError = null) }
            }
            is RegisterEvent.SelectedCountry -> {
                _uiState.update { it.copy(countryCode = event.code, countryIsoCode = event.isoCode) }
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                // Handle in details screen
            }
            is RegisterEvent.Register -> {
                val currentState = uiState.value
                if (currentState.name.isNotEmpty() && currentState.password.isNotEmpty()) {
                    register()
                } else {
                    validateAndNavigate()
                }
            }
        }
    }

    fun setPhoneNumberError(error: UiText) {
        _uiState.update { it.copy(phoneNumberError = error) }
    }

    fun setEmailError(error: UiText) {
        _uiState.update { it.copy(emailError = error) }
    }

    private fun validateAndNavigate() {
        val currentState = uiState.value
        when (currentState.selectedOption) {
            "Email" -> {
                val emailError = authUseCases.validateEmail(currentState.email)
                if (emailError != null) {
                    _uiState.update { it.copy(emailError = emailError) }
                } else {
                    viewModelScope.launch {
                        val available = authUseCases.checkEmailAvailable(currentState.email)
                        if (available) {
                            _eventFlow.emit(UiEvent.NavigateToRegisterDetails)
                        } else {
                            _uiState.update { it.copy(emailError = UiText.DynamicString("Account already exists")) }
                        }
                    }
                }
            }
            "Phone" -> {
                val phoneError = authUseCases.validatePhoneNumber(
                    currentState.phoneNumber,
                    currentState.countryIsoCode
                )
                if (phoneError != null) {
                    _uiState.update { it.copy(phoneNumberError = phoneError) }
                } else {
                    viewModelScope.launch {
                        val available = authUseCases.checkPhoneAvailable(currentState.phoneNumber)
                        if (available) {
                            _eventFlow.emit(UiEvent.NavigateToRegisterDetails)
                        } else {
                            _uiState.update { it.copy(phoneNumberError = UiText.DynamicString("Account already exists")) }
                        }
                    }
                }
            }
        }
    }

    private fun register() {
        val currentState = uiState.value

        // Validate password locally first with strict validation
        val passwordError = authUseCases.validatePassword(currentState.password, strictValidation = true)
        if (passwordError != null) {
            _uiState.update { it.copy(passwordError = passwordError) }
            return
        }

        // Clear any previous password error and proceed with registration
        _uiState.update { it.copy(passwordError = null) }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, emailError = null, nameError = null, phoneNumberError = null) }
            val (email, phone) = when (currentState.selectedOption) {
                "Email" -> currentState.email to null
                "Phone" -> null to currentState.phoneNumber
                else -> currentState.email to null
            }
            val registerResult = authUseCases.register(
                email = email,
                phone = phone,
                name = currentState.name,
                password = currentState.password,
                password_confirm = currentState.password // Assuming password confirm is same as password
            )
            _uiState.update { it.copy(
                emailError = registerResult.emailError,
                phoneNumberError = registerResult.phoneNumberError,
                nameError = registerResult.nameError,
                passwordError = registerResult.passwordError,
                isLoading = false
            ) }
            when(registerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.OnRegister)
                }
                is Resource.Error -> {
                    // Errors are handled via field errors in uiState
                }
                null -> {}
            }
        }
    }
}
