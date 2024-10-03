pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
//    val storageUrl =  "https://storage.googleapis.com"
    repositories {
        google()
        mavenCentral()
//        flatDir { dirs("cool") }

        maven {
            url = uri("https://storage.googleapis.com/download.flutter.io")
        }
        maven { url = uri("https://jitpack.io") }


        /*maven {
            //        url 'C:/AddSDK/repo'
            //            url = uri("file:///Users/rahul-admin/Documents/Android%20Projects/Sales-Repo")
            //            url = uri("/Users/rahul-admin/Documents/Flutter Projects/flutter_module/build/host/outputs/repo")
            url = uri("$rootDir/libs/repo")  // Adjust to the path where you copied the repo
//            url = uri("https://drive.google.com/drive/folders/1wPO6HurDmEiwbTlzSCDYSKFFFUobjJ8r")  // Adjust to the path where you copied the repo
        }*/


    }
}

rootProject.name = "Koltin-Playground"
include(":app")
//include(":flutter_module_library")
include(":cool_library")
