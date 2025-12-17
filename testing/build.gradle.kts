plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.dailyquiztest.testing"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    compileOnly(project(":app"))

    ksp(libs.hilt.compiler)

    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
}
