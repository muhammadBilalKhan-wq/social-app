package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText

class ValidateUsername {

    operator fun invoke(name: String): UiText? {
        if (name.isBlank()) {
            return UiText.StringResource(R.string.error_name_blank)
        }
        if (name.length < 2) {
            return UiText.StringResource(R.string.error_name_too_short)
        }
        return null
    }
}
