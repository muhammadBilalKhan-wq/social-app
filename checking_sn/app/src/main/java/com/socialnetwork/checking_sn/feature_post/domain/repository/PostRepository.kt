package com.socialnetwork.checking_sn.feature_post.domain.repository

import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.core.util.Resource

interface PostRepository {

    suspend fun getPostsForFollows(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>>

    suspend fun likePost(postId: String): Resource<Unit>

    suspend fun unlikePost(postId: String): Resource<Unit>

    suspend fun addComment(postId: String, content: String): Resource<Unit>

    suspend fun createPost(content: String): Resource<Post>
}
