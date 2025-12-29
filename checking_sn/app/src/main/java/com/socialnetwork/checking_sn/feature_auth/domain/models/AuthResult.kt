package com.socialnetwork.checking_sn.feature_auth.domain.models

import com.socialnetwork.checking_sn.core.util.SimpleResource
import com.socialnetwork.checking_sn.core.util.UiText

data class AuthResult(
    val emailError: UiText? = null,
    val nameError: UiText? = null,
    val passwordError: UiText? = null,
    val passwordConfirmError: UiText? = null,
    val result: SimpleResource? = null
)
