plugins {
    base
}

allprojects {
    group = "com.qloak"
    version = "${property("base_version")}+mc${property("minecraft_version")}"

    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.terraformersmc.com/")
        maven("https://maven.shedaniel.me/")
        maven("https://maven.blamejared.com/")
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set((project.property("java_version") as String).toInt())
    }

    tasks.withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.WARN
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
        filesMatching("META-INF/neoforge.mods.toml") {
            expand("version" to project.version)
        }
    }
}
