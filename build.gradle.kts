buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
}

extra["kotlin_version"] = "2.1.21"
extra["lifecycle_version"] = "2.10.0"
extra["nav_version"] = "2.9.6"
extra["compose_version"] = "1.12.1"

allprojects {
    repositories {
        google()
        maven {
            url = uri("https://maven.google.com")
        }
        maven {
            url = uri("https://jitpack.io")
        }
        mavenCentral()
    }
}

configurations.configureEach {
    resolutionStrategy {
        force("org.jetbrains:annotations:13.0")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
