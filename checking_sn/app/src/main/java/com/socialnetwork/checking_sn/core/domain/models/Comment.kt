package com.socialnetwork.checking_sn.core.domain.models

/**
 * Data class representing a comment on a social media post.
 * Comments are owned by posts and contain user information and content.
 */
data class Comment(
    val id: String,           // Unique identifier for the comment
    val postId: String,       // ID of the post this comment belongs to
    val userId: String,       // ID of the user who wrote the comment
    val username: String,     // Display name of the comment author
    val content: String,      // Text content of the comment
    val timestamp: Long       // Creation timestamp in milliseconds since epoch
)
