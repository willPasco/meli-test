plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.will.core.network.api"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    api(libs.kotlinx.serialization.json)
}
