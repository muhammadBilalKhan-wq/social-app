package com.socialnetwork.checking_sn.di

import android.content.Context
import com.socialnetwork.checking_sn.core.util.EnvironmentConfig
import com.socialnetwork.checking_sn.feature_post.data.remote.PostApi
import com.socialnetwork.checking_sn.feature_post.data.repository.PostRepositoryImpl
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(okHttpClient: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .baseUrl(EnvironmentConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(postApi: PostApi, @ApplicationContext context: Context): PostRepository {
        return PostRepositoryImpl(postApi, context)
    }
}
