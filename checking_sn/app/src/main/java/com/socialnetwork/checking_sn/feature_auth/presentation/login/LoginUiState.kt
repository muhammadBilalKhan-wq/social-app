package com.socialnetwork.checking_sn.feature_auth.presentation.login

import com.socialnetwork.checking_sn.core.util.UiText

data class LoginUiState(
    val selectedOption: String = "Email",
    val email: String = "",
    val phoneNumber: String = "",
    val countryCode: String = "+92",
    val countryIsoCode: String = "PK",
    val emailError: UiText? = null,
    val phoneNumberError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false
)
