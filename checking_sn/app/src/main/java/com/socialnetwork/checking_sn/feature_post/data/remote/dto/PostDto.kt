package com.socialnetwork.checking_sn.feature_post.data.remote.dto

import com.socialnetwork.checking_sn.core.domain.models.Post

/**
 * Data Transfer Object for Post data received from the backend API.
 * This DTO matches the backend PostSerializer response.
 */
data class PostDto(
    val id: Int,
    val author: Int,
    val author_username: String,
    val author_name: String,
    val content: String,
    val image_url: String?,
    val postImageUrl: String?,
    val created_at: String,
    val updated_at: String
) {
    /**
     * Converts this DTO to the domain Post model.
     * Comments are initialized as empty since they're not included in basic post data.
     * @return Post domain object with the same data
     */
    fun toPost(): Post {
        return Post(
            id = id.toString(),
            userId = author.toString(),
            username = author_username,
            imageUrl = postImageUrl ?: "",
            description = content,
            timestamp = parseTimestamp(created_at),
            likeCount = 0, // Not implemented yet
            commentCount = 0, // Not implemented yet
            isLiked = false, // Not implemented yet
            isOwnPost = false, // Not implemented yet
            comments = emptyList(),
            isCommentsExpanded = false
        )
    }

    private fun parseTimestamp(timestamp: String): Long {
        // Simple ISO timestamp to milliseconds conversion
        // This is a basic implementation - you might want to use a proper date library
        return try {
            // Assuming format: "2024-01-06T14:30:00Z"
            val date = java.time.Instant.parse(timestamp)
            date.toEpochMilli()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}
