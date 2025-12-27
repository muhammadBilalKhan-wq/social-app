package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String
)