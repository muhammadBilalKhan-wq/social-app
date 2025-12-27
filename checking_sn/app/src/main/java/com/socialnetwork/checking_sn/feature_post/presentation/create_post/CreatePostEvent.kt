package com.socialnetwork.checking_sn.feature_post.presentation.create_post

sealed class CreatePostEvent {
    data class EnteredContent(val value: String): CreatePostEvent()
    object Post: CreatePostEvent()
}
