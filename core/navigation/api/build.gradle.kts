plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.core.navigation.api"
}

dependencies {
    api(libs.androidx.navigation.compose)
}