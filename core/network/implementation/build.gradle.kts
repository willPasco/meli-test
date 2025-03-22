plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.will.core.network.implementation"
}

dependencies {
    implementation(project(":core:network:api"))

    implementation(libs.retrofit)
    implementation(libs.okhttp)
}