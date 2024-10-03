plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}

android {
    namespace = "com.antartica.flutter_module_library"
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


    repositories {
        /*maven {
            url = uri("https://storage.googleapis.com/download.flutter.io")
        }*/
        maven {
            // Local repo containing your AAR files
            url = uri("$rootDir/flutter_module_library/libs/repo")
        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Replace with your correct AAR dependencies
//    debugImplementation("com.example.my_flutter_module:flutter_debug:1.0")
//    releaseImplementation("com.example.my_flutter_module:flutter_release:1.0")
    implementation("com.example.my_flutter_module:flutter_release:1.0")
}



/*publishing {
    publications {
        create("release", MavenPublication::class) {
            groupId = "com.github.gohil-rahul-tft"
            artifactId = "flutter-module-library"
            version = "1.0.1"

            artifact("$rootDir/libs/repo")
        }
    }

}*/

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["release"])
            groupId = "com.github.gohil-rahul-tft"
            artifactId = "flutter-module-library"
            version = "1.0.3"
        }
    }
}
