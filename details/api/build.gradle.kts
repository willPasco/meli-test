plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.will.details.api"
}

dependencies {
    implementation(project(":core:navigation:api"))
    implementation(libs.kotlinx.serialization.json)
}
