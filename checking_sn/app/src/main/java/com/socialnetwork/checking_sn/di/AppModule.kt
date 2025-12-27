package com.socialnetwork.checking_sn.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.repository.AuthRepositoryImpl
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository
import com.socialnetwork.checking_sn.feature_post.data.remote.PostApi
import com.socialnetwork.checking_sn.feature_post.data.repository.PostRepositoryImpl
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import com.socialnetwork.checking_sn.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")
                val requestBuilder = it.request().newBuilder()
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                it.proceed(requestBuilder.build())
            }
            .build()
    }
}
