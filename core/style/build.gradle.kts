plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    id(libs.plugins.paparazzi.get().pluginId)
}

android {
    namespace = "com.will.core.style"
}

dependencies {
    implementation(libs.androidx.material3)
    testImplementation(libs.bundles.unit.test)
}
