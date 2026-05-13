# qLibs

Personal library for Minecraft 1.21.1 modding. Dual-targets Fabric and NeoForge via Architectury.

## Modules

- [qCore](qcore) — Platform, scheduling, logging
- [qConfig](qconfig) — Config builder + file persistence
- [qNet](qnet) — Packet networking
- [qRender](qrender) — HUD, widgets, particles
- [qData](qdata) — JSON, registries, player data
- [qWorld](qworld) — Teleport helpers
- [qInventory](qinventory) — Item builder, stack utils
- [qMath](qmath) — Vectors, easing
- [qCompat](qcompat) — Soft compat stubs

## Building

```bash
./gradlew build
```

Artifacts in each module's `build/libs/`.
