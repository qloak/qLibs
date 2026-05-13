plugins {
    id("java-library")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("maven-publish")
}

architectury {
    common("fabric", "neoforge")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modApi("dev.architectury:architectury:${property("architectury_api_version")}")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "qlibs-common"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
