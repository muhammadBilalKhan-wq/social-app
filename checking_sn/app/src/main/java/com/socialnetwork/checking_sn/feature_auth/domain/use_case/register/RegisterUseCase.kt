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

    suspend operator fun invoke(email: String, username: String, password: String, password_confirm: String): AuthResult {
        Log.d("RegisterUseCase", "invoke() called - BEFORE validation")
        val emailError = validateEmail(email)
        val usernameError = validateUsername(username)
        val passwordError = validatePassword(password)
        val passwordConfirmError = if (password != password_confirm) UiText.DynamicString("Passwords do not match") else null

        if (emailError != null || usernameError != null || passwordError != null || passwordConfirmError != null) {
            Log.d("RegisterUseCase", "Validation failed: email=$emailError, username=$usernameError, password=$passwordError, passwordConfirm=$passwordConfirmError")
            return AuthResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                passwordConfirmError = passwordConfirmError
            )
        }
        Log.d("RegisterUseCase", "Validation passed - calling repository.register()")
        return repository.register(email, username, password, password_confirm)
    }
}
