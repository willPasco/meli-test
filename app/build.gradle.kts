plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.meli"

    defaultConfig {
        applicationId = "com.will.meli"
        versionCode = 1
        versionName = "1.0"
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
    implementation(libs.androidx.material3)

    implementation(libs.koin.android)

    implementation(project(":core:network:implementation"))
    implementation(project(":core:navigation:implementation"))
    implementation(project(":listing:implementation"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}