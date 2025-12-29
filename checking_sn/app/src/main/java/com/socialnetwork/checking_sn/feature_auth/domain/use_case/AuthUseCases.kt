package com.socialnetwork.checking_sn.feature_auth.domain.use_case

import com.socialnetwork.checking_sn.core.domain.use_case.ValidateEmail
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePassword
import com.socialnetwork.checking_sn.core.domain.use_case.ValidatePhoneNumber
import com.socialnetwork.checking_sn.core.domain.use_case.ValidateUsername
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_email_available.CheckEmailAvailableUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.check_phone_available.CheckPhoneAvailableUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.get_me.GetMeUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.login.LoginUseCase
import com.socialnetwork.checking_sn.feature_auth.domain.use_case.register.RegisterUseCase

data class AuthUseCases(
    val validateEmail: ValidateEmail,
    val validateUsername: ValidateUsername,
    val validatePassword: ValidatePassword,
    val validatePhoneNumber: ValidatePhoneNumber,
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val getMe: GetMeUseCase,
    val checkEmailAvailable: CheckEmailAvailableUseCase,
    val checkPhoneAvailable: CheckPhoneAvailableUseCase
)
