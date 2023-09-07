pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven ("https://jitpack.io")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

rootProject.name = "StoreHarmonyTest"
include(":app")
 