package com.socialnetwork.checking_sn.feature_auth.domain.use_case.login

import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail
    ) {

    suspend operator fun invoke(email: String): AuthResult {
        val emailError = validateEmail(email)

        if (emailError != null) {
            return AuthResult(
                emailError = emailError
            )
        }
        return repository.login(email, "")
    }
}
