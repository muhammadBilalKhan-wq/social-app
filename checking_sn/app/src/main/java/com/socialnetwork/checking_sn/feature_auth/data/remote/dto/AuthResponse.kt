package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class AuthResponse(
    val userId: String,
    val access: String,
    val refresh: String
)
