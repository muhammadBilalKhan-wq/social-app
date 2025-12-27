package com.socialnetwork.checking_sn.di

import android.util.Log
import android.content.Context
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateUsername
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.repository.AuthRepositoryImpl
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.get_me.GetMeUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.login.LoginUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.register.RegisterUseCase
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
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        val api = Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
        Log.d("AuthModule", "AuthApi initialized with baseUrl: ${AuthApi.BASE_URL}")
        return api
    }

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, authManager: AuthManager): AuthRepository {
        return AuthRepositoryImpl(api, authManager)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            validateEmail = ValidateEmail(),
            validateUsername = ValidateUsername(),
            validatePassword = ValidatePassword(),
            login = LoginUseCase(repository, ValidateEmail()),
            register = RegisterUseCase(repository, ValidateEmail(), ValidateUsername(), ValidatePassword()),
            getMe = GetMeUseCase(repository)
        )
    }
}
