package com.socialnetwork.checking_sn.core.domain.models

/**
 * Data class representing a social media post with all necessary fields for display and interaction.
 * This model contains user information, content, engagement metrics, interaction states, and comments.
 */
data class Post(
    val id: String,           // Unique identifier for the post
    val userId: String,       // ID of the user who created the post
    val username: String,     // Display name of the post author
    val imageUrl: String,     // URL of any attached image (empty string if none)
    val description: String,  // Main content/text of the post
    val timestamp: Long,      // Creation timestamp in milliseconds since epoch
    val likeCount: Int,       // Number of likes this post has received (default: 0)
    val commentCount: Int,    // Number of comments on this post
    val isLiked: Boolean,     // Whether the current user has liked this post (default: false)
    val isOwnPost: Boolean,   // Whether this post was created by the current user
    val comments: List<Comment> = emptyList(), // List of comments on this post
    val isCommentsExpanded: Boolean = false    // Whether comments are currently expanded
)
