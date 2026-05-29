plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ericmschmidt.classicsreader.latin.views"
    compileSdk = 36
    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "com.ericmschmidt.classicsreader.latin.views"
        minSdk = 24
        targetSdk = 36
        versionCode = 21
        versionName = "2.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("24"))
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":views"))
    implementation(project(":latinreader"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
