package com.socialnetwork.checking_sn.di

import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.data.remote.AuthInterceptor
import com.socialnetwork.checking_sn.core.data.remote.SecureHttpLoggingInterceptor
import com.socialnetwork.checking_sn.core.data.remote.TokenAuthenticator
import com.socialnetwork.checking_sn.core.util.EnvironmentConfig
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        secureTokenManager: SecureTokenManager
    ): TokenAuthenticator {
        // Create a basic OkHttpClient for TokenAuthenticator to use for refresh requests
        val basicClient = OkHttpClient.Builder()
            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
        return TokenAuthenticator(basicClient, secureTokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(secureTokenManager: SecureTokenManager): AuthInterceptor {
        return AuthInterceptor(secureTokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenAuthenticator: TokenAuthenticator,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        // TODO: Change to false for production release
        val isDebug = true // Set to false for production builds
        val loggingInterceptor = SecureHttpLoggingInterceptor.create(
            isDebug = isDebug
        )
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .addInterceptor(authInterceptor)  // Clean auth header handling
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(EnvironmentConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
