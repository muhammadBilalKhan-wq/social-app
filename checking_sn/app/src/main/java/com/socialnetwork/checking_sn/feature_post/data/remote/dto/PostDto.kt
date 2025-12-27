package com.socialnetwork.checking_sn.feature_post.data.remote.dto

import com.socialnetwork.checking_sn.core.domain.models.Post

/**
 * Data Transfer Object for Post data received from the backend API.
 * This DTO mirrors the Post domain model but is used for network communication.
 * Note: Comments are typically fetched separately for performance reasons.
 */
data class PostDto(
    val id: String,           // Unique identifier for the post
    val userId: String,       // ID of the user who created the post
    val username: String,     // Display name of the post author
    val imageUrl: String,     // URL of any attached image
    val description: String,  // Main content/text of the post
    val timestamp: Long,      // Creation timestamp in milliseconds since epoch
    val likeCount: Int,       // Number of likes this post has received
    val commentCount: Int,    // Number of comments on this post
    val isLiked: Boolean,     // Whether the current user has liked this post
    val isOwnPost: Boolean    // Whether this post was created by the current user
) {
    /**
     * Converts this DTO to the domain Post model.
     * Comments are initialized as empty since they're fetched separately.
     * @return Post domain object with the same data
     */
    fun toPost(): Post {
        return Post(
            id = id,
            userId = userId,
            username = username,
            imageUrl = imageUrl,
            description = description,
            timestamp = timestamp,
            likeCount = likeCount,
            commentCount = commentCount,
            isLiked = isLiked,
            isOwnPost = isOwnPost,
            comments = emptyList(), // Comments fetched separately
            isCommentsExpanded = false // Default collapsed state
        )
    }
}
