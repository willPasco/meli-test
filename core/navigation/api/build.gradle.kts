plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.will.core.navigation.api"
}

dependencies {
    api(libs.androidx.navigation.compose)
}