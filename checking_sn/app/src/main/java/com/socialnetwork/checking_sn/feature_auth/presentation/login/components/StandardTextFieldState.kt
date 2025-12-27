package com.socialnetwork.checking_sn.feature_auth.presentation.login.components

import com.socialnetwork.checking_sn.core.util.UiText

data class StandardTextFieldState(
    val text: String = "",
    val error: UiText? = null
)

data class PasswordTextFieldState(
    val text: String = "",
    val error: UiText? = null,
    val isPasswordVisible: Boolean = false
)