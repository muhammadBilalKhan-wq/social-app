# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ============================================
# AGGRESSIVE R8 OPTIMIZATION RULES
# These rules enable maximum code shrinking
# while maintaining compatibility
# ============================================

# Kotlin Specific Optimizations
-assumenosideeffects class kotlin.coroutines.Continuation {
    public <init>(java.lang.Object, kotlin.coroutines.ContinuationInterceptor);
}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ============================================
# KEEP RULES - Required for libraries to work
# These prevent R8 from removing necessary code
# ============================================

# Retrofit & OkHttp
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Coil
-dontwarn coil.**
-keep class coil.** { *; }

# Security Crypto
-keep class androidx.security.crypto.** { *; }

# Phone Number Library - keep only needed classes
-keep class com.googlecode.libphonenumber.** { *; }
-dontwarn com.googlecode.libphonenumber.**

# Remove unused metadata from phonelib
-assumenosideeffects class com.googlecode.libphonenumber.PhoneNumberUtil {
    public static *** getInstance(...);
}

# Keep data classes for serialization
-keep class com.socialnetwork.checking_sn.feature_post.data.remote.dto.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ============================================
# AGGRESSIVE SHRINKING
# Remove unused code aggressively
# ============================================

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Remove logging in release (safe - won't affect behavior)
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ============================================
# STRIP DEBUG METADATA
# ============================================

-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizationpasses 5

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
