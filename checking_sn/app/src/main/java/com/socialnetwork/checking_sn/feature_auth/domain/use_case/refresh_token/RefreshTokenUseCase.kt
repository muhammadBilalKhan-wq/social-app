package com.socialnetwork.checking_sn.feature_auth.domain.use_case.refresh_token

import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository

class RefreshTokenUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AuthResult {
        return repository.refreshToken()
    }
}
