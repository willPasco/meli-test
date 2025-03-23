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
    implementation(project(":core:network:api"))
    implementation(project(":core:style"))
    implementation(project(":details:api"))

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)

    testImplementation(libs.bundles.unit.test)
}
