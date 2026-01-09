package com.socialnetwork.checking_sn.feature_post.presentation.create_post

import com.socialnetwork.checking_sn.core.util.UiText

data class CreatePostState(
    val content: String = "",
    val selectedImageUri: String? = null, // Base64 encoded image for now
    val isSubmitting: Boolean = false,
    val error: UiText? = null
)
