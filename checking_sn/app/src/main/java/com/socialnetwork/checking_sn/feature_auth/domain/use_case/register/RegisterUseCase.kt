package com.socialnetwork.checking_sn.feature_auth.domain.use_case.register

import android.util.Log
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateUsername
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail,
    private val validateUsername: ValidateUsername,
    private val validatePassword: ValidatePassword
    ) {

    suspend operator fun invoke(email: String, name: String, password: String, password_confirm: String): AuthResult {
        Log.d("RegisterUseCase", "invoke() called - BEFORE validation")
        val emailError = validateEmail(email)
        val nameError = validateUsername(name)
        val passwordError = validatePassword(password)
        val passwordConfirmError = if (password != password_confirm) UiText.DynamicString("Passwords do not match") else null

        if (emailError != null || nameError != null || passwordError != null || passwordConfirmError != null) {
            Log.d("RegisterUseCase", "Validation failed: email=$emailError, name=$nameError, password=$passwordError, passwordConfirm=$passwordConfirmError")
            return AuthResult(
                emailError = emailError,
                nameError = nameError,
                passwordError = passwordError,
                passwordConfirmError = passwordConfirmError
            )
        }
        Log.d("RegisterUseCase", "Validation passed - calling repository.register()")
        return repository.register(email, name, password, password_confirm)
    }
}
