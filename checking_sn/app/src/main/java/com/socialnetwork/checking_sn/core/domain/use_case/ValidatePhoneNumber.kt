package com.socialnetwork.checking_sn.core.domain.use_case

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.socialnetwork.checking_sn.core.util.UiText

class ValidatePhoneNumber {

    private val phoneUtil = PhoneNumberUtil.getInstance()

    operator fun invoke(phoneNumber: String, countryIsoCode: String): UiText? {
        if (phoneNumber.isBlank()) {
            return UiText.DynamicString("Phone number cannot be empty")
        }

        return try {
            // Parse the phone number with the country code
            val parsedNumber: PhoneNumber = phoneUtil.parse(phoneNumber, countryIsoCode)

            // Check if the number is valid
            if (!phoneUtil.isValidNumber(parsedNumber)) {
                UiText.DynamicString("Please enter a valid phone number for your selected country")
            } else {
                null // Valid phone number
            }
        } catch (e: NumberParseException) {
            UiText.DynamicString("Please enter a valid phone number for your selected country")
        } catch (e: Exception) {
            UiText.DynamicString("Invalid phone number format")
        }
    }
}
