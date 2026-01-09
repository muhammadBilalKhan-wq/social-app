package com.socialnetwork.checking_sn.feature_post.data.repository

import android.content.Context
import android.util.Base64
import android.util.Log
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_post.data.remote.PostApi
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val context: Context
) : PostRepository {

    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        return try {
            val response = postApi.getPosts()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null && apiResponse.successful) {
                    val posts = apiResponse.data?.map { it.toPost() } ?: emptyList()
                    Resource.Success(data = posts)
                } else {
                    Resource.Error(UiText.DynamicString(apiResponse?.message ?: "Failed to load posts"))
                }
            } else {
                Resource.Error(UiText.DynamicString("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PostRepository", "Error loading posts", e)
            Resource.Error(UiText.DynamicString("Network error: ${e.message}"))
        }
    }

    override suspend fun likePost(postId: String): Resource<Unit> {
        // Not implemented yet - return success for now
        return Resource.Success(Unit)
    }

    override suspend fun unlikePost(postId: String): Resource<Unit> {
        // Not implemented yet - return success for now
        return Resource.Success(Unit)
    }

    override suspend fun addComment(postId: String, content: String): Resource<Unit> {
        // Not implemented yet - return success for now
        return Resource.Success(Unit)
    }

    override suspend fun createPost(content: String, imageUrl: String?): Resource<Post> {
        return try {
            val contentBody = content.toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageUrl?.let { base64String ->
                withContext(Dispatchers.IO) {
                    try {
                        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                        val requestBody = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("image_url", "image.jpg", requestBody)
                    } catch (e: Exception) {
                        Log.e("PostRepository", "Error decoding base64 image", e)
                        null
                    }
                }
            }

            val response = postApi.createPost(contentBody, imagePart)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null && apiResponse.successful) {
                    val post = apiResponse.data?.toPost()
                    if (post != null) {
                        Resource.Success(data = post)
                    } else {
                        Resource.Error(UiText.DynamicString("Invalid response data"))
                    }
                } else {
                    Resource.Error(UiText.DynamicString(apiResponse?.message ?: "Failed to create post"))
                }
            } else {
                Resource.Error(UiText.DynamicString("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PostRepository", "Error creating post", e)
            Resource.Error(UiText.DynamicString("Network error: ${e.message}"))
        }
    }
}
