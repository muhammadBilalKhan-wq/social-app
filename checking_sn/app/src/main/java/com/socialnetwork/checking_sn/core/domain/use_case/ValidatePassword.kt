package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText
import java.util.regex.Pattern

class ValidatePassword {

    operator fun invoke(password: String): UiText? {
        if (password.isBlank()) {
            return UiText.StringResource(R.string.error_password_blank)
        }
        if (password.length < 8) {
            return UiText.StringResource(R.string.error_password_too_short)
        }
        val hasUppercase = Pattern.compile("[A-Z]").matcher(password).find()
        val hasLowercase = Pattern.compile("[a-z]").matcher(password).find()
        val hasDigit = Pattern.compile("[0-9]").matcher(password).find()
        if (!hasUppercase || !hasLowercase || !hasDigit) {
            return UiText.StringResource(R.string.error_password_invalid)
        }
        return null
    }
}