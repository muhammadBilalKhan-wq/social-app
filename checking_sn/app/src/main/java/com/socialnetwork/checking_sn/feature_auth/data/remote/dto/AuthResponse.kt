package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class AuthResponse(
    val access: String,
    val refresh: String,
    val userId: String
)

data class LoginRegisterResponse(
    val success: Boolean,
    val user_id: Int? = null,
    val access: String? = null,
    val refresh: String? = null,
    val error: String? = null
)
