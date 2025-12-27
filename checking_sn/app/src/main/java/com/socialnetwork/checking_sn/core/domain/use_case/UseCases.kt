package com.socialnetwork.checking_sn.core.domain.use_case

data class UseCases(
    val validateEmail: ValidateEmail,
    val validateUsername: ValidateUsername,
    val validatePassword: ValidatePassword
)