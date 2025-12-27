package com.socialnetwork.checking_sn.feature_auth.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String): LoginEvent()
    object Login: LoginEvent()
}
