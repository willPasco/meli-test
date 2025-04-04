plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.google.services.get().pluginId)
    id(libs.plugins.crashlytics.get().pluginId)
}

android {
    namespace = "com.will.meli"

    defaultConfig {
        applicationId = "com.will.meli"
        versionCode = 1
        versionName = "0.4"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.material3)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:network:implementation"))
    implementation(project(":core:navigation:api"))
    implementation(project(":core:navigation:implementation"))
    implementation(project(":core:style"))
    implementation(project(":core:logger"))
    implementation(project(":listing:implementation"))
    implementation(project(":details:implementation"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}