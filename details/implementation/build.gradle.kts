plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.convention.compose)
    id(libs.plugins.paparazzi.get().pluginId)
}

android {
    namespace = "com.will.details.implementation"

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(project(":details:api"))
    implementation(project(":core:navigation:api"))
    implementation(project(":core:network:api"))
    implementation(project(":core:style"))
    implementation(project(":core:logger"))

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.bundles.unit.test)
}
