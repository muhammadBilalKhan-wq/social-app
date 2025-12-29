package com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_phone_available

import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class CheckPhoneAvailableUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(phone: String): Boolean {
        return (repository as? com.socialnetwork.checking_sn.feature_auth.data.repository.AuthRepositoryImpl)?.checkPhoneAvailable(phone) ?: false
    }
}
