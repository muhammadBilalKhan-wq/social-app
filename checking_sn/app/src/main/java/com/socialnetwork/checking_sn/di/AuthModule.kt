package com.socialnetwork.checking_sn.di

import android.content.Context
import com.socialnetwork.checking_sn.core.data.manager.AuthManager
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePhoneNumber
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateUsername
import com.socialnetwork.checking_sn.feature_auth.data.remote.AuthApi
import com.socialnetwork.checking_sn.feature_auth.data.repository.AuthRepositoryImpl
import com.socialnetwork.checking_sn.feature_auth.domain.repository.AuthRepository
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.AuthUseCases
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_email_available.CheckEmailAvailableUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_phone_available.CheckPhoneAvailableUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.get_me.GetMeUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.login.LoginUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.register.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {



    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authManager: AuthManager, authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authManager, authApi)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            validateEmail = ValidateEmail(),
            validateUsername = ValidateUsername(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            login = LoginUseCase(repository, ValidateEmail(), ValidatePassword()),
            register = RegisterUseCase(repository, ValidateEmail(), ValidateUsername(), ValidatePassword(), ValidatePhoneNumber()),
            getMe = GetMeUseCase(repository),
            checkEmailAvailable = CheckEmailAvailableUseCase(repository),
            checkPhoneAvailable = CheckPhoneAvailableUseCase(repository)
        )
    }
}
