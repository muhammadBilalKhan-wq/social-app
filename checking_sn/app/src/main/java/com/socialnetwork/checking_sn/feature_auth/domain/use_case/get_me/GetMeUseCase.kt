package com.socialnetwork.checking_sn.feature_auth.domain.use_case.get_me

import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class GetMeUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AuthResult {
        return repository.getMe()
    }
}
