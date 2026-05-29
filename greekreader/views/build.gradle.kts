plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ericmschmidt.classicsreader.greek.views"
    compileSdk = 36

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.ericmschmidt.classicsreader.greek.views"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
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
    testOptions {
        suites {
            register("adaptiveTest") {
                assets {
                }
                targets {
                    register("default") {
                    }
                }
                useJunitEngine {
                    inputs.add(com.android.build.api.dsl.AgpTestSuiteInputParameters.TESTED_APKS)
                    includeEngines.add("journeys-test-engine")
                    enginesDependencies.add("org.junit.platform:junit-platform-launcher:1.13.4")
                    enginesDependencies.add("org.junit.platform:junit-platform-engine:1.13.4")
                    enginesDependencies.add("com.android.tools.journeys:journeys-junit-engine:0.2.1")
                }
                targetVariants.add("debug")
            }
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":views"))
    implementation(project(":greekreader"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
