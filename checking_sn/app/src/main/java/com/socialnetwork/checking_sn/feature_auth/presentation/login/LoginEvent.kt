package com.socialnetwork.checking_sn.feature_auth.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String): LoginEvent()
    data class EnteredPhoneNumber(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    data class SelectedOption(val option: String): LoginEvent()
    data class SelectedCountry(val code: String, val isoCode: String): LoginEvent()
    object Login: LoginEvent()
}
