package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class LoginRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String
)
