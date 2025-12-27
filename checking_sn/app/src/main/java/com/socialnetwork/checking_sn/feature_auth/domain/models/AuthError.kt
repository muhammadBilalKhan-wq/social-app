package com.socialnetwork.checking_sn.feature_auth.domain.models

sealed class AuthError {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}