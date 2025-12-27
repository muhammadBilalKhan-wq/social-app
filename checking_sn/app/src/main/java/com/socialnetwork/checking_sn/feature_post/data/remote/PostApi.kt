package com.socialnetwork.checking_sn.feature_post.data.remote

import com.socialnetwork.checking_sn.feature_post.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {

    @GET("/api/feed")
    suspend fun getPostsForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<PostDto>

    @POST("/api/post/like")
    suspend fun likePost(@Query("postId") postId: String)

    @POST("/api/post/unlike")
    suspend fun unlikePost(@Query("postId") postId: String)

    @POST("/api/post/comment")
    suspend fun addComment(
        @Query("postId") postId: String,
        @Query("content") content: String
    )

    @POST("/api/post/create")
    suspend fun createPost(@Query("content") content: String): PostDto

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}
