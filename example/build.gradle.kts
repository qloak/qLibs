plugins {
    id("java-library")
    id("dev.architectury.loom")
    id("maven-publish")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modApi("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
    modApi(project(":fabric"))
    compileOnly(project(":common", configuration = "namedElements"))
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "qlibs-example"
            from(components["java"])
        }
    }
}
