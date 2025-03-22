plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.listing.implementation"
}

dependencies {
    implementation(project(":core:navigation:api"))

    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)
}
