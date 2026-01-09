package com.socialnetwork.checking_sn.feature_post.data.remote.dto

/**
 * Data Transfer Object for creating a new post.
 * Matches the expected request body for POST /api/posts/
 */
data class CreatePostRequest(
    val content: String,
    val image_url: String? = null
)
