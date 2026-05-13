plugins {
    id("java-library")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("maven-publish")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

configurations {
    maybeCreate("common")
    maybeCreate("shadowCommon")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modApi("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
    modApi("dev.architectury:architectury-fabric:${property("architectury_api_version")}")
    compileOnly(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    "common"(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    "shadowCommon"(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

java {
    withSourcesJar()
}

tasks.jar {
    dependsOn(":common:transformProductionFabric")
    from(configurations.getByName("shadowCommon").map {
        if (it.isDirectory) it else zipTree(it)
    })
    duplicatesStrategy = DuplicatesStrategy.WARN
    archiveClassifier.set("dev")
}

tasks.remapJar {
    injectAccessWidener = true
    input.set(tasks.jar.get().archiveFile)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "qlibs-fabric"
            from(components["java"])
        }
    }
}
