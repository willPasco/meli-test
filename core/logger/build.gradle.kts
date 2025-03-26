plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.will.core.logger"
}

dependencies {

    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.crashlytics)
    api(libs.timber)
}