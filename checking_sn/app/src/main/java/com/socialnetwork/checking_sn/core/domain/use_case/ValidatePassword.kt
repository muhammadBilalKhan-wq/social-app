package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText
import java.util.regex.Pattern

class ValidatePassword {

    operator fun invoke(password: String, strictValidation: Boolean = true): UiText? {
        if (password.isBlank()) {
            return UiText.DynamicString("Password cannot be blank")
        }
        if (password.length < 6) {
            return UiText.DynamicString("Password must be at least 6 characters long.")
        }

        if (strictValidation) {
            if (password.length < 8) {
                return UiText.DynamicString("Password must be at least 8 characters long.")
            }
            if (password.length > 32) {
                return UiText.DynamicString("Password must be at most 32 characters long.")
            }
            if (!Pattern.compile("[A-Z]").matcher(password).find()) {
                return UiText.DynamicString("Password must contain at least one uppercase letter.")
            }
            if (!Pattern.compile("[a-z]").matcher(password).find()) {
                return UiText.DynamicString("Password must contain at least one lowercase letter.")
            }
            if (!Pattern.compile("\\d").matcher(password).find()) {
                return UiText.DynamicString("Password must contain at least one number.")
            }
            if (!Pattern.compile("[!@#$%^&*]").matcher(password).find()) {
                return UiText.DynamicString("Password must contain at least one special character (!@#$%^&*).")
            }
        }
        return null
    }

    fun validateForLogin(password: String): UiText? {
        if (password.isBlank()) {
            return UiText.DynamicString("Password cannot be blank")
        }
        if (password.length < 6) {
            return UiText.DynamicString("Password must be at least 6 characters long.")
        }
        return null
    }
}
