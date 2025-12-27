package com.socialnetwork.checking_sn.feature_auth.data.remote.dto

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val profile: ProfileResponse,
    val followers_count: Int,
    val following_count: Int
)

data class ProfileResponse(
    val display_name: String?,
    val bio: String?,
    val avatar: String?,
    val created_at: String
)
