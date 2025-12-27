package com.socialnetwork.checking_sn.feature_post.presentation.feed

sealed class FeedEvent {
    data class LikePost(val postId: String, val originalIsLiked: Boolean, val originalLikeCount: Int): FeedEvent()
    data class UnlikePost(val postId: String, val originalIsLiked: Boolean, val originalLikeCount: Int): FeedEvent()
    data class CreatePost(val description: String): FeedEvent()
    object LoadPosts: FeedEvent()
    object LoadMorePosts: FeedEvent()
    data class ToggleComments(val postId: String): FeedEvent()
    data class AddComment(val postId: String, val content: String): FeedEvent()
}
