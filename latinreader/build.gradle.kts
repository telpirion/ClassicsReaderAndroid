plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.ericmschmidt.latinreader"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
        targetSdk = 36
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("24"))
        }
    }
}

dependencies {
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":views"))
    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.text.google.fonts)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}
