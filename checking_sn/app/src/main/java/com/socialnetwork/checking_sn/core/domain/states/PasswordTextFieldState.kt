package com.socialnetwork.checking_sn.core.domain.states

data class PasswordTextFieldState(
    val text: String = "",
    val error: String? = null,
    val isPasswordVisible: Boolean = false
)