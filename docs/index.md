# qLibs Docs

qLibs is a personal Minecraft modding library for 1.21.1, dual-targeting Fabric and NeoForge via Architectury.

## Quick Start

Add the common module to your `build.gradle.kts`:

```kotlin
dependencies {
    compileOnly(project(":common", configuration = "namedElements"))
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modApi("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
}
```

Or for NeoForge, reference the common source set similarly.

## Modules

| Module | Purpose | Link |
|--------|---------|------|
| qCore | Platform, scheduling, logging | [qcore.md](qcore.md) |
| qConfig | Config builder + registry | [qconfig.md](qconfig.md) |
| qNet | Packet networking | [qnet.md](qnet.md) |
| qRender | HUD, widgets, particles | [qrender.md](qrender.md) |
| qData | JSON, registries, player data | [qdata.md](qdata.md) |
| qWorld | Teleport, feature helpers | [qworld.md](qworld.md) |
| qInventory | Item builders, stack utils | [qinventory.md](qinventory.md) |
| qMath | Vectors, easing | [qmath.md](qmath.md) |
| qCompat | Soft compat stubs | [qcompat.md](qcompat.md) |

## Design Notes

- Everything in `common/` is platform-agnostic. Fabric/NeoForge modules only hold `@ExpectPlatform` implementations.
- No hard dependencies on anything except Minecraft, Architectury, and SLF4J.
- Javadoc is intentionally light. Read the source if you need details.
