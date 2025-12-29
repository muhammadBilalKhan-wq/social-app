package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class CreateAccountRequest(
    val email: String,
    val name: String,
    val password: String
)
