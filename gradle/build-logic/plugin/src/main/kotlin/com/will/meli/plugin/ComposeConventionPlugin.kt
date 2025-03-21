package com.will.meli.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
            }

            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureCompose(this, libs)
            }

            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureCompose(this, libs)
            }
        }
    }

    private fun Project.configureCompose(
        commonExtensions: CommonExtension<*, *, *, *, *, *>,
        libs: VersionCatalog
    ) {

        commonExtensions.apply {
            buildFeatures {
                compose = true
            }
        }

        dependencies {
            val composeBom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(composeBom))
            add("implementation", libs.findLibrary("androidx.ui").get())
            add("implementation", libs.findLibrary("androidx.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())

            add("androidTestImplementation", platform(composeBom))
            add("androidTestImplementation", libs.findLibrary("androidx.ui").get())

            add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
        }
    }
}
