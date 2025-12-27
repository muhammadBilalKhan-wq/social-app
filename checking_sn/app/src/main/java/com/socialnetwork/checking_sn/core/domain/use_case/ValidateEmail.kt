package com.socialnetwork.checking_sn.core.domain.use_case

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.util.UiText

class ValidateEmail {

    operator fun invoke(email: String): UiText? {
        if (email.isBlank()) {
            return UiText.StringResource(R.string.error_email_blank)
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return UiText.StringResource(R.string.error_email_invalid)
        }
        return null
    }
}