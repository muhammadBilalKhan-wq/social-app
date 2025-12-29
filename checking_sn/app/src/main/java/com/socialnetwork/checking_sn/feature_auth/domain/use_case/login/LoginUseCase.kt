package com.socialnetwork.checking_sn.feature_auth.domain.use_case.login

import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val validatePassword: com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
    ) {

    suspend operator fun invoke(identifier: String, password: String): AuthResult {
        val passwordError = validatePassword(password)

        if (passwordError != null) {
            return AuthResult(
                passwordError = passwordError
            )
        }
        return repository.login(identifier, password)
    }
}
