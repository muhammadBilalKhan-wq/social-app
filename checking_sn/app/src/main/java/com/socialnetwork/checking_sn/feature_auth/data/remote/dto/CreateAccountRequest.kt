package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String,
    val password_confirm: String
)
