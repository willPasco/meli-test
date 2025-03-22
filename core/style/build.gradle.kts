plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.will.core.style"
}

dependencies {
    implementation(libs.androidx.material3)
}
