package com.socialnetwork.checking_sn.feature_auth.data.remote

import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRegisterResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register/")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): Response<LoginRegisterResponse>

    @POST("/api/auth/login/")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginRegisterResponse>

    @POST("/api/auth/check_email/")
    suspend fun checkEmail(
        @Body request: Map<String, String>
    ): Response<Map<String, Boolean>>

    @POST("/api/auth/check_phone/")
    suspend fun checkPhone(
        @Body request: Map<String, String>
    ): Response<Map<String, Boolean>>

    @GET("/api/auth/me/")
    suspend fun getMe(): Response<BasicApiResponse<UserResponse>>

    companion object {
        // Use 10.0.2.2 for Android emulator, or your computer's IP for physical device
        // Current: 192.168.1.6 - Update this if your IP changes
        const val BASE_URL = "http://192.168.1.6:8000/"
    }
}
