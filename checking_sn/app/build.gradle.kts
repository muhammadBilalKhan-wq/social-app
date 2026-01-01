plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.socialnetwork.checking_sn"
    compileSdk = 34 // Updated to a stable version

    defaultConfig {
        applicationId = "com.socialnetwork.checking_sn"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Limit ABIs for smaller APK size
        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86")
            abiFilters.add("x86_64")
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.32:8000/\"")
            buildConfigField("String", "ENVIRONMENT", "\"DEBUG\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.checking-sn.com/\"")
            buildConfigField("String", "ENVIRONMENT", "\"PRODUCTION\"")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Flavor dimensions are required for product flavors
    flavorDimensions += "environment"

    // Product flavors for different environments
    // Usage:
    // - Dev: ./gradlew assembleDevDebug (connects to local backend)
    // - Staging: ./gradlew assembleStagingRelease (connects to staging backend)
    // - Prod: ./gradlew assembleProdRelease (connects to production backend)
    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.32:8000/\"")
            buildConfigField("String", "ENVIRONMENT", "\"DEVELOPMENT\"")
            dimension = "environment"
        }
        create("staging") {
            buildConfigField("String", "BASE_URL", "\"https://api-staging.checking-sn.com/\"")
            buildConfigField("String", "ENVIRONMENT", "\"STAGING\"")
            dimension = "environment"
        }
        create("prod") {
            buildConfigField("String", "BASE_URL", "\"https://api.checking-sn.com/\"")
            buildConfigField("String", "ENVIRONMENT", "\"PRODUCTION\"")
            dimension = "environment"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation(libs.hilt.navigation.compose)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Material Icons Core - needed for password visibility and other icons
    implementation(libs.androidx.compose.material.icons.core)

    // Phone number validation
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.25")

    // Security crypto for encrypted shared preferences
    implementation(libs.androidx.security.crypto)
}
