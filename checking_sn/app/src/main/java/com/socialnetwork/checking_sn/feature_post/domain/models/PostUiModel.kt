package com.socialnetwork.checking_sn.feature_post.domain.models

data class PostUiModel(
    val id: String,
    val username: String,
    val profileImageUrl: String,
    val postImageUrl: String,
    val caption: String,
    val likesCount: Int,
    val isLiked: Boolean,
    val timestamp: String
)
