plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.will.core.network.api"
}

dependencies {
    implementation(libs.koin.android)

    api(libs.retrofit)
    api(libs.kotlinx.serialization.json)
}
