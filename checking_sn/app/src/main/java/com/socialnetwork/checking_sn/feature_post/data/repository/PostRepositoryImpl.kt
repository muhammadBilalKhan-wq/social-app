package com.socialnetwork.checking_sn.feature_post.data.repository

import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository

class PostRepositoryImpl() : PostRepository {

    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        // Backend removed: Return empty list for demo purposes
        return Resource.Success(data = emptyList())
    }

    override suspend fun likePost(postId: String): Resource<Unit> {
        // Backend removed: Simulate success for demo purposes
        return Resource.Success(Unit)
    }

    override suspend fun unlikePost(postId: String): Resource<Unit> {
        // Backend removed: Simulate success for demo purposes
        return Resource.Success(Unit)
    }

    override suspend fun addComment(postId: String, content: String): Resource<Unit> {
        // Backend removed: Simulate success for demo purposes
        return Resource.Success(Unit)
    }

    override suspend fun createPost(content: String): Resource<Post> {
        // Backend removed: Return mock post for demo purposes
        val mockPost = Post(
            id = "mock_post_id",
            userId = "mock_user_id",
            username = "MockUser",
            imageUrl = "",
            description = content,
            timestamp = System.currentTimeMillis(),
            likeCount = 0,
            commentCount = 0,
            isLiked = false,
            isOwnPost = true,
            comments = emptyList(),
            isCommentsExpanded = false
        )
        return Resource.Success(data = mockPost)
    }
}
