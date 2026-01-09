package com.socialnetwork.checking_sn.feature_post.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.domain.models.Comment
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 5
    }

    private val _state = MutableStateFlow(FeedState(isInitialLoading = true))
    val state: StateFlow<FeedState> = _state

    private var currentPage = 0

    init {
        loadInitialPosts()
    }

    fun onEvent(event: FeedEvent) {
        when (event) {
            is FeedEvent.LoadMorePosts -> {
                loadMorePosts()
            }
            is FeedEvent.ToggleComments -> {
                toggleComments(event.postId)
            }
            is FeedEvent.AddComment -> {
                addComment(event.postId, event.content)
            }
            is FeedEvent.LikePost -> {
                likePost(event.postId, event.originalIsLiked, event.originalLikeCount)
            }
            is FeedEvent.UnlikePost -> {
                unlikePost(event.postId, event.originalIsLiked, event.originalLikeCount)
            }
            else -> {}
        }
    }

    /**
     * Handles like/unlike action for a post by toggling its liked state and updating the like count.
     * This method performs an optimistic update to immediately reflect the change in the UI
     * while maintaining immutability of the state.
     *
     * @param postId The unique identifier of the post to like/unlike
     */
    fun onLikeClicked(postId: String) {
        val post = state.value.posts.find { it.id == postId } ?: return
        val originalIsLiked = post.isLiked
        val originalLikeCount = post.likeCount
        val isLiking = !originalIsLiked

        // Optimistic update
        val updatedPosts = state.value.posts.map { p ->
            if (p.id == postId) {
                p.copy(
                    isLiked = isLiking,
                    likeCount = if (isLiking) p.likeCount + 1 else p.likeCount - 1
                )
            } else {
                p
            }
        }
        _state.value = state.value.copy(posts = updatedPosts)

        // Send event for repository call
        val event = if (isLiking) FeedEvent.LikePost(postId, originalIsLiked, originalLikeCount) else FeedEvent.UnlikePost(postId, originalIsLiked, originalLikeCount)
        onEvent(event)
    }

    /**
     * Toggles the expanded state of comments for a specific post.
     * This allows users to show or hide comments inline within the feed.
     *
     * @param postId The unique identifier of the post whose comments should be toggled
     */
    private fun toggleComments(postId: String) {
        val updatedPosts = state.value.posts.map { post ->
            if (post.id == postId) {
                post.copy(isCommentsExpanded = !post.isCommentsExpanded)
            } else {
                post
            }
        }
        _state.value = state.value.copy(posts = updatedPosts)
    }

    /**
     * Adds a new comment to a specific post.
     * Calls backend API and appends new comment locally on success.
     *
     * @param postId The unique identifier of the post to add the comment to
     * @param content The text content of the comment
     */
    private fun addComment(postId: String, content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            val result = repository.addComment(postId, content)
            if (result is com.socialnetwork.checking_sn.core.util.Resource.Success) {
                val newComment = Comment(
                    id = UUID.randomUUID().toString(),
                    postId = postId,
                    userId = "currentUser", // Mock current user
                    username = "Test User", // Mock username
                    content = content,
                    timestamp = System.currentTimeMillis()
                )

                val updatedPosts = state.value.posts.map { post ->
                    if (post.id == postId) {
                        post.copy(
                            comments = post.comments + newComment,
                            commentCount = post.commentCount + 1
                        )
                    } else {
                        post
                    }
                }
                _state.value = state.value.copy(posts = updatedPosts)
            }
        }
    }

    private fun likePost(postId: String, originalIsLiked: Boolean, originalLikeCount: Int) {
        viewModelScope.launch {
            val result = repository.likePost(postId)
            if (result is com.socialnetwork.checking_sn.core.util.Resource.Error) {
                // Rollback
                val updatedPosts = state.value.posts.map { p ->
                    if (p.id == postId) {
                        p.copy(
                            isLiked = originalIsLiked,
                            likeCount = originalLikeCount
                        )
                    } else {
                        p
                    }
                }
                _state.value = state.value.copy(posts = updatedPosts)
            }
        }
    }

    private fun unlikePost(postId: String, originalIsLiked: Boolean, originalLikeCount: Int) {
        viewModelScope.launch {
            val result = repository.unlikePost(postId)
            if (result is com.socialnetwork.checking_sn.core.util.Resource.Error) {
                // Rollback
                val updatedPosts = state.value.posts.map { p ->
                    if (p.id == postId) {
                        p.copy(
                            isLiked = originalIsLiked,
                            likeCount = originalLikeCount
                        )
                    } else {
                        p
                    }
                }
                _state.value = state.value.copy(posts = updatedPosts)
            }
        }
    }

    fun addPost(post: Post) {
        // Add new post to the top
        _state.value = state.value.copy(posts = listOf(post) + state.value.posts)
    }

    /**
     * Loads the initial set of posts for pagination.
     * Shows initial loading state, then loads the first page of posts.
     */
    private fun loadInitialPosts() {
        viewModelScope.launch {
            val result = repository.getPostsForFollows(currentPage, PAGE_SIZE)
            when (result) {
                is com.socialnetwork.checking_sn.core.util.Resource.Success -> {
                    val posts = result.data ?: emptyList()
                    _state.value = state.value.copy(
                        posts = posts,
                        isInitialLoading = false,
                        hasMorePosts = posts.size == PAGE_SIZE
                    )
                }
                is com.socialnetwork.checking_sn.core.util.Resource.Error -> {
                    _state.value = state.value.copy(
                        isInitialLoading = false,
                        error = result.uiText
                    )
                }
            }
        }
    }

    /**
     * Loads additional posts when user scrolls near the bottom.
     * Appends new posts to existing list and updates pagination state.
     */
    private fun loadMorePosts() {
        if (state.value.isLoadingMore || !state.value.hasMorePosts) return

        viewModelScope.launch {
            _state.value = state.value.copy(isLoadingMore = true)
            currentPage++
            val result = repository.getPostsForFollows(currentPage, PAGE_SIZE)
            when (result) {
                is com.socialnetwork.checking_sn.core.util.Resource.Success -> {
                    val newPosts = result.data ?: emptyList()
                    val updatedPosts = state.value.posts + newPosts
                    _state.value = state.value.copy(
                        posts = updatedPosts,
                        isLoadingMore = false,
                        hasMorePosts = newPosts.size == PAGE_SIZE
                    )
                }
                is com.socialnetwork.checking_sn.core.util.Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingMore = false
                    )
                }
            }
        }
    }
}
