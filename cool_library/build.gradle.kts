plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}

android {
    namespace = "com.antartica.cool_library"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

repositories {
    google()
    mavenCentral()
    flatDir { dirs("cool") }
    maven {
        url = uri("https://storage.googleapis.com/download.flutter.io")
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "cool", "include" to listOf("*.jar"))))
    implementation(files("${rootDir}/cool/flutter_release-1.0.aar"))

    implementation("io.flutter:flutter_embedding_release:1.0.0-edd8546116457bdf1c5bdfb13ecb9463d2bb5ed4")
    implementation("io.flutter:armeabi_v7a_release:1.0.0-edd8546116457bdf1c5bdfb13ecb9463d2bb5ed4")
    implementation("io.flutter:arm64_v8a_release:1.0.0-edd8546116457bdf1c5bdfb13ecb9463d2bb5ed4")
    implementation("io.flutter:x86_64_release:1.0.0-edd8546116457bdf1c5bdfb13ecb9463d2bb5ed4")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}

publishing {
    publications {
        create<MavenPublication>("release") {
//            from(components["release"])
            groupId = "com.github.gohil-rahul-tft"
            artifactId = "flutter-cool-library"
            version = "1.0.5"
        }
    }
}