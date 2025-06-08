pluginManagement {
    val kotlinVersion: String by settings
    val applicationVersion: String by settings
    val hiltVersion: String by settings
    val kspVersion: String by settings

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.android") version kotlinVersion
        id("com.android.application") version applicationVersion
        id("com.google.dagger.hilt.android") version hiltVersion
        id("com.google.devtools.ksp") version kspVersion
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Barcode Scanner"
include(":app")
