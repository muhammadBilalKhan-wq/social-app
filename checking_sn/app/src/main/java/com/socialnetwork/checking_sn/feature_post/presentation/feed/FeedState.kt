package com.socialnetwork.checking_sn.feature_post.presentation.feed

import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.core.util.UiText

data class FeedState(
    val posts: List<Post> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMorePosts: Boolean = true,
    val error: UiText? = null
)
