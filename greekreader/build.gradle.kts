plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.ericmschmidt.greekreader"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("24"))
        }
    }
    lint {
        targetSdk = 36
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":views"))
    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.text.google.fonts)
}
