package com.socialnetwork.checking_sn.feature_auth.presentation.register

sealed class RegisterEvent {
    data class EnteredEmail(val value: String): RegisterEvent()
    data class EnteredPhoneNumber(val value: String): RegisterEvent()
    data class EnteredUsername(val value: String): RegisterEvent()
    data class EnteredPassword(val value: String): RegisterEvent()
    data class EnteredPasswordConfirm(val value: String): RegisterEvent()
    data class SelectedOption(val option: String): RegisterEvent()
    data class SelectedCountry(val code: String, val isoCode: String): RegisterEvent()
    object TogglePasswordVisibility: RegisterEvent()
    object Register: RegisterEvent()
}
