package com.socialnetwork.checking_sn.feature_auth.domain.use_case.register

import android.util.Log
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePhoneNumber
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateUsername
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail,
    private val validateUsername: ValidateUsername,
    private val validatePassword: ValidatePassword,
    private val validatePhoneNumber: ValidatePhoneNumber
    ) {

    suspend operator fun invoke(email: String?, phone: String?, name: String, password: String, password_confirm: String): AuthResult {
        Log.d("RegisterUseCase", "invoke() called - BEFORE validation")
        val nameError = validateUsername(name)
        val passwordError = validatePassword(password)
        val passwordConfirmError = if (password != password_confirm) UiText.DynamicString("Passwords do not match") else null

        // Additional validation for email if provided
        val emailError = if (email != null) validateEmail(email) else null

        if (nameError != null || passwordError != null || passwordConfirmError != null || emailError != null) {
            Log.d("RegisterUseCase", "Validation failed: name=$nameError, password=$passwordError, passwordConfirm=$passwordConfirmError, email=$emailError")
            return AuthResult(
                nameError = nameError,
                passwordError = passwordError,
                passwordConfirmError = passwordConfirmError,
                emailError = emailError
            )
        }
        Log.d("RegisterUseCase", "Validation passed - calling repository.register()")
        return repository.register(email, phone, name, password, password_confirm)
    }
}
