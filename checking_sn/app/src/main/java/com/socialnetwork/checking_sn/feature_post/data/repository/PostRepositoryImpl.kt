package com.socialnetwork.checking_sn.feature_post.data.repository

import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.core.util.Resource
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.feature_post.data.remote.PostApi
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository {

    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        return try {
            val posts = api.getPostsForFollows(
                page = page,
                pageSize = pageSize
            ).map { it.toPost() }
            Resource.Success(data = posts)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.DynamicString("Couldn't reach server, please check your internet connection.")
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.DynamicString("Oops, something went wrong!")
            )
        }
    }

    override suspend fun likePost(postId: String): Resource<Unit> {
        return try {
            api.likePost(postId)
            Resource.Success(Unit)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.DynamicString("Couldn't reach server, please check your internet connection.")
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.DynamicString("Oops, something went wrong!")
            )
        }
    }

    override suspend fun unlikePost(postId: String): Resource<Unit> {
        return try {
            api.unlikePost(postId)
            Resource.Success(Unit)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.DynamicString("Couldn't reach server, please check your internet connection.")
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.DynamicString("Oops, something went wrong!")
            )
        }
    }

    override suspend fun addComment(postId: String, content: String): Resource<Unit> {
        return try {
            api.addComment(postId, content)
            Resource.Success(Unit)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.DynamicString("Couldn't reach server, please check your internet connection.")
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.DynamicString("Oops, something went wrong!")
            )
        }
    }

    override suspend fun createPost(content: String): Resource<Post> {
        return try {
            val postDto = api.createPost(content)
            Resource.Success(data = postDto.toPost())
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.DynamicString("Couldn't reach server, please check your internet connection.")
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.DynamicString("Oops, something went wrong!")
            )
        }
    }
}
