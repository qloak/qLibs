# qLibs

Personal library for Minecraft 1.21.1 modding. Fabric + NeoForge via Architectury.

## What's Inside

| Module | What it does |
|--------|-------------|
| **qCore** | Platform detection, tick scheduler, logging |
| **qConfig** | Config builder with categories + validation |
| **qNet** | Custom packet payloads via Architectury |
| **qRender** | HUD overlays, widgets (labels, panels, progress bars), particles |
| **qData** | JSON loaders, player data store, resource conditions |
| **qWorld** | Teleport helpers |
| **qInventory** | Item builder, stack utils |
| **qMath** | Vectors, easing |
| **qCompat** | JEI/REI/EMI, Curios stubs |

## Building

```bash
./gradlew build
```

## License

LGPL-3.0. See [LICENSE](LICENSE).
