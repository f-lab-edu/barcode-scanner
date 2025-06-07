// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("com.google.dagger.hilt.android") apply false
    id("com.google.devtools.ksp") apply false
}

buildscript {
    val ossPluginVersion: String by project
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.android.gms:oss-licenses-plugin:$ossPluginVersion")
    }
}