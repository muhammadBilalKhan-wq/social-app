package com.socialnetwork.checking_sn.feature_auth.domain.repository

import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, name: String, password: String, password_confirm: String): AuthResult
    suspend fun getMe(): AuthResult
}
