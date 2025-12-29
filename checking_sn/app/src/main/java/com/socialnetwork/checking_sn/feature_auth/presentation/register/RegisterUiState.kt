package com.socialnetwork.checking_sn.feature_auth.presentation.register

import com.socialnetwork.checking_sn.core.util.UiText

data class RegisterUiState(
    val selectedOption: String = "Email", // "Email" or "Phone"
    val email: String = "",
    val emailError: UiText? = null,
    val phoneNumber: String = "",
    val phoneNumberError: UiText? = null,
    val countryCode: String = "+92",
    val countryIsoCode: String = "PK",
    val username: String = "",
    val usernameError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false
)
