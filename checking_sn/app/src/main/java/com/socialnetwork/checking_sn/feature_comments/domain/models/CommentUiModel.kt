package com.socialnetwork.checking_sn.feature_comments.domain.models

data class CommentUiModel(
    val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val text: String,
    val timestamp: String,
    val likesCount: Int,
    val isLiked: Boolean
)
