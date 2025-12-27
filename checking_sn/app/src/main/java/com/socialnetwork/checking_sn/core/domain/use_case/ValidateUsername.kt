package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText

class ValidateUsername {

    operator fun invoke(username: String): UiText? {
        if (username.isBlank()) {
            return UiText.StringResource(R.string.error_username_blank)
        }
        if (username.length < 4) {
            return UiText.StringResource(R.string.error_username_too_short)
        }
        return null
    }
}