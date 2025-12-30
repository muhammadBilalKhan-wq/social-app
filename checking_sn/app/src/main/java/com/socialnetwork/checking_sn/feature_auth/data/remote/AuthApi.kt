package com.socialnetwork.checking_sn.feature_auth.data.remote

import com.socialnetwork.checking_sn.core.data.dto.response.BasicApiResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.AuthResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRegisterResponse
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.RefreshTokenRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.LoginRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.CreateAccountRequest
import com.socialnetwork.checking_sn.feature_auth.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/register/")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): Response<LoginRegisterResponse>

    @POST("api/auth/login/")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginRegisterResponse>

    @POST("api/auth/check_email/")
    suspend fun checkEmail(
        @Body request: Map<String, String>
    ): Response<Map<String, Boolean>>

    @POST("api/auth/check_phone/")
    suspend fun checkPhone(
        @Body request: Map<String, String>
    ): Response<Map<String, Boolean>>

    @POST("api/auth/refresh/")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<AuthResponse>

    @POST("api/auth/logout/")
    suspend fun logout(
        @Body request: RefreshTokenRequest
    ): Response<Map<String, String>>

    @GET("api/auth/me/")
    suspend fun getMe(): Response<BasicApiResponse<UserResponse>>
}
