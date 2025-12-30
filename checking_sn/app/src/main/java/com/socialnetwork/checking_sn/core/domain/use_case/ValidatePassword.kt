package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText
import java.util.regex.Pattern

class ValidatePassword {

    operator fun invoke(password: String): UiText? {
        if (password.isBlank()) {
            return UiText.StringResource(R.string.error_password_blank)
        }
        if (password.length < 6) {
            return UiText.StringResource(R.string.error_password_too_short)
        }
        return null
    }
}
