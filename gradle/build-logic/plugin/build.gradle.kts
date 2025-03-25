plugins {
    `kotlin-dsl`
}

group = "com.will.meli.plugin"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("conventionPluginAndroidApplication") {
            id = "meli.android.application"
            implementationClass = "com.will.meli.plugin.AndroidApplicationConventionPlugin"
        }
        register("conventionPluginCompose") {
            id = "meli.compose"
            implementationClass = "com.will.meli.plugin.ComposeConventionPlugin"
        }
        register("conventionPluginAndroidLibrary") {
            id = "meli.android.library"
            implementationClass = "com.will.meli.plugin.AndroidLibraryConventionPlugin"
        }
    }
}