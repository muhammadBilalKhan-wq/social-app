package com.socialnetwork.checking_sn.feature_auth.data.remote

import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register/")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/auth/login/")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/auth/me/")
    suspend fun getMe(): BasicApiResponse<UserResponse>

    companion object {
        const val BASE_URL = "http://192.168.1.6:8000/"
    }
}
