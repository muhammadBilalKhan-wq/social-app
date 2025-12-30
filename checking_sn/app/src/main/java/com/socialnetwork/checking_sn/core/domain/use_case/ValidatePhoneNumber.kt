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

        // First check basic length constraints (7-12 digits for international numbers)
        val digitCount = phoneNumber.count { it.isDigit() }
        if (digitCount < 7) {
            return UiText.DynamicString("Phone number is too short")
        }
        if (digitCount > 12) {
            return UiText.DynamicString("Phone number is too long")
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
