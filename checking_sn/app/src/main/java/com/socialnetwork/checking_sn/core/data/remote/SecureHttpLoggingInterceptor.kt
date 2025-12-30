package com.socialnetwork.checking_sn.core.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Security-aware HTTP logging interceptor that never logs sensitive information.
 * - Release builds: No logging
 * - Debug builds: Logs headers and basic info, but redacts Authorization headers
 */
class SecureHttpLoggingInterceptor private constructor(
    private val level: Level
) : Interceptor {

    enum class Level {
        NONE,       // No logging (release)
        BASIC,      // Log request method, URL, response code (debug)
        HEADERS     // Log headers but redact sensitive ones (debug)
    }

    companion object {
        private const val TAG = "OkHttp"
        private val UTF8 = Charset.forName("UTF-8")

        fun create(isDebug: Boolean): SecureHttpLoggingInterceptor {
            val level = if (isDebug) Level.HEADERS else Level.NONE
            return SecureHttpLoggingInterceptor(level)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (level == Level.NONE) {
            return chain.proceed(chain.request())
        }

        val request = chain.request()
        val startNs = System.nanoTime()

        // Log request
        logRequest(request)

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Log.w(TAG, "--> END ${request.method} (failed: ${e.message})")
            throw e
        }

        return logResponse(response, startNs)
    }

    private fun logRequest(request: Request) {
        when (level) {
            Level.BASIC -> {
                Log.d(TAG, "--> ${request.method} ${request.url}")
            }
            Level.HEADERS -> {
                Log.d(TAG, "--> ${request.method} ${request.url}")

                // Log headers but redact sensitive ones
                request.headers.forEach { (name, value) ->
                    if (isSensitiveHeader(name)) {
                        Log.d(TAG, "$name: ***REDACTED***")
                    } else {
                        Log.d(TAG, "$name: $value")
                    }
                }

                if (request.body != null) {
                    Log.d(TAG, "--> END ${request.method} (${request.body?.contentLength() ?: -1}-byte body)")
                } else {
                    Log.d(TAG, "--> END ${request.method}")
                }
            }
            Level.NONE -> {
                // No logging
            }
        }
    }

    private fun logResponse(response: Response, startNs: Long): Response {
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        when (level) {
            Level.BASIC -> {
                Log.d(TAG, "<-- ${response.code} ${response.message} ${response.request.url} (${tookMs}ms)")
            }
            Level.HEADERS -> {
                Log.d(TAG, "<-- ${response.code} ${response.message} ${response.request.url} (${tookMs}ms)")

                // Log response headers but redact sensitive ones
                response.headers.forEach { (name, value) ->
                    if (isSensitiveHeader(name)) {
                        Log.d(TAG, "$name: ***REDACTED***")
                    } else {
                        Log.d(TAG, "$name: $value")
                    }
                }

                Log.d(TAG, "<-- END HTTP")
            }
            Level.NONE -> {
                // No logging
            }
        }

        return response
    }

    private fun isSensitiveHeader(name: String): Boolean {
        return name.equals("Authorization", ignoreCase = true) ||
               name.equals("X-Auth-Token", ignoreCase = true) ||
               name.equals("Cookie", ignoreCase = true) ||
               name.contains("token", ignoreCase = true)
    }
}
