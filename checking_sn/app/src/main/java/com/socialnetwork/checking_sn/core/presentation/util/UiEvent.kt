package com.socialnetwork.checking_sn.core.presentation.util

import com.socialnetwork.checking_sn.core.util.UiText

sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText): UiEvent()
    object OnLogin: UiEvent()
    object OnRegister: UiEvent()
    object NavigateBack: UiEvent()
    data class OnPostCreated(val description: String): UiEvent()
}
