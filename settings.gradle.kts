pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases/")
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("dev.architectury.loom") version "1.13-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
}

rootProject.name = "qLibs"

include("common")
include("fabric")
include("neoforge")
include("example")
