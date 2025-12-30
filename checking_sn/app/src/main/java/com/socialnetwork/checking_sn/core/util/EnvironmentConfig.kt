package com.socialnetwork.checking_sn.core.util

/**
 * Environment configuration utility class.
 * Provides centralized access to BuildConfig values and environment-specific logic.
 */
object EnvironmentConfig {

    val baseUrl: String
        get() = com.socialnetwork.checking_sn.BuildConfig.BASE_URL

    val environment: String
        get() = com.socialnetwork.checking_sn.BuildConfig.ENVIRONMENT

    val isDebug: Boolean
        get() = environment == "DEBUG" || environment == "DEVELOPMENT"

    val isStaging: Boolean
        get() = environment == "STAGING"

    val isProduction: Boolean
        get() = environment == "PRODUCTION"

    /**
     * Get environment-specific display name
     */
    val environmentDisplayName: String
        get() = when (environment) {
            "DEVELOPMENT" -> "Dev"
            "STAGING" -> "Staging"
            "PRODUCTION" -> "Prod"
            else -> environment
        }

    /**
     * Check if cleartext traffic is allowed (for debugging purposes)
     */
    val allowsCleartextTraffic: Boolean
        get() = isDebug || isStaging // Only allow in non-production environments
}
