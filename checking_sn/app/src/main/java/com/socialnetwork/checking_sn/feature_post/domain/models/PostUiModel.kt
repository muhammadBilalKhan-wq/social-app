package com.socialnetwork.checking_sn.feature_post.domain.models

/**
 * UI Model for displaying posts in the UI layer.
 * This is a simplified representation of a post for display purposes.
 */
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
