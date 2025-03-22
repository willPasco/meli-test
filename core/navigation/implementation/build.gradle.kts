plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.core.navigation.implementation"
}

dependencies {
    implementation(project(":core:navigation:api"))

    implementation(libs.koin.android)
}