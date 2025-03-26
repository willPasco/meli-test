plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.core.navigation.implementation"
}

dependencies {
    implementation(project(":core:navigation:api"))
    implementation(project(":core:logger"))

    implementation(libs.koin.android)
    implementation(libs.androidx.activity.compose)
}