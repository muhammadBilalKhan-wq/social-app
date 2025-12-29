package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class CreateAccountRequest(
    val email: String? = null,
    val phone: String? = null,
    val name: String,
    val password: String
)
