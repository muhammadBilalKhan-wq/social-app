package com.socialnetwork.checking_sn.feature_post.data.remote

import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.feature_post.data.remote.dto.PostDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {

    @GET("/api/posts/")
    suspend fun getPosts(): Response<BasicApiResponse<List<PostDto>>>

    @Multipart
    @POST("/api/posts/")
    suspend fun createPost(
        @Part("content") content: RequestBody,
        @Part imageUrl: MultipartBody.Part?
    ): Response<BasicApiResponse<PostDto>>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}
