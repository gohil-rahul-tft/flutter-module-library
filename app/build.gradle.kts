plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("maven-publish")
}


android {
    namespace = "com.antartica.koltin_playground"
    compileSdk = 34

    kapt {
        correctErrorTypes = true
    }

    defaultConfig {
        applicationId = "com.antartica.koltin_playground"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }




    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation(project(":flutter_module_library"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // BioMetric
    implementation(libs.androidx.biometric)

    // Hilt
//    implementation (libs.androidx.hilt.lifecycle.viewmodel)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

//    implementation(files("libs/flutter_release-1.0.aar"))
//    implementation(files("libs/flutter_debug-1.0.aar"))

//    implementation("io.flutter:flutter_embedding_release:3.10.5")

//    debugImplementation ("com.salespandadm.app:flutter_debug:1.0")
//    releaseImplementation ("com.salespandadm.app:flutter_release:1.0")


//    implementation ("com.github.gohil-rahul-tft:flutter-module-library:1.0.0")


//    debugImplementation("com.example.my_flutter_module:flutter_debug:1.0")
//    implementation("com.example.my_flutter_module:flutter_release:1.0")

//    debugImplementation 'com.example.my_flutter_module:flutter_debug:1.0'
//    profileImplementation 'com.example.my_flutter_module:flutter_profile:1.0'
//    releaseImplementation 'com.example.my_flutter_module:flutter_release:1.0'
}


