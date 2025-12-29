package com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_email_available

import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class CheckEmailAvailableUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Boolean {
        return (repository as? com.socialnetwork.checking_sn.feature_auth.data.repository.AuthRepositoryImpl)?.checkEmailAvailable(email) ?: false
    }
}
