// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.paparazzi.gradle.plugin)
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id(libs.plugins.detekt.get().pluginId) version libs.versions.detekt apply false
    id(libs.plugins.google.services.get().pluginId) version libs.versions.google.services apply false
    id(libs.plugins.crashlytics.get().pluginId) version libs.versions.crashlytics apply false
}