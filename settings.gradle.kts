pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Meli Test"
include(":app")
include(":core:network:api")
include(":core:network:implementation")
include(":core:navigation:api")
include(":core:navigation:implementation")
include(":listing:api")
include(":listing:implementation")
