package com.socialnetwork.checking_sn.di

import com.socialnetwork.checking_sn.feature_post.data.repository.PostRepositoryImpl
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostRepository(): PostRepository {
        return PostRepositoryImpl()
    }
}
