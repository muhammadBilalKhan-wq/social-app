package com.socialnetwork.checking_sn.feature_auth.presentation.login

import com.socialnetwork.checking_sn.core.util.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false
)
