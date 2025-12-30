package com.socialnetwork.checking_sn.feature_auth.domain.repository

import com.socialnetwork.checking_sn.feature_auth.domain.models.AuthResult

interface AuthRepository {
    suspend fun login(identifier: String, password: String): AuthResult
    suspend fun register(email: String?, phone: String?, name: String, password: String, password_confirm: String): AuthResult
    suspend fun logout(): AuthResult
    suspend fun refreshToken(): AuthResult
    suspend fun getMe(): AuthResult
}
