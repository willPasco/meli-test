package com.will.meli.plugin

import com.android.build.api.dsl.CommonExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.baseConfig(
    commonExtensions: CommonExtension<*, *, *, *, *, *>,
    libs: VersionCatalog
) {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    configDetekt(libs = libs)

    commonExtensions.apply {

        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig.minSdk = libs.findVersion("minSdk").get().toString().toInt()
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        compileOptions.sourceCompatibility = JavaVersion.VERSION_17
        compileOptions.targetCompatibility = JavaVersion.VERSION_17
    }
}

private fun Project.configDetekt(libs: VersionCatalog) {
    pluginManager.apply(libs.findPlugin("detekt").get().get().pluginId)

    extensions.configure<DetektExtension> {
        config.setFrom(project.rootProject.file("gradle/detekt/config/config.yml"))
        buildUponDefaultConfig = true
        allRules = false
        parallel = true
    }

    tasks.withType(Detekt::class.java).configureEach {

        finalizedBy("build")

        reports {
            html.required.set(true)
            xml.required.set(true)
        }
    }

    dependencies {
        add("detektPlugins", libs.findLibrary("detekt-formatting").get())
    }
}
