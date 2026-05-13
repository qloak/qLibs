# qWorld

World helpers and teleportation utilities.

## DimensionTeleport

```java
// teleport to exact coordinates in another dimension
DimensionTeleport.teleport(serverPlayer, targetLevel, x, y, z, yaw, pitch);

// using a Vec3
DimensionTeleport.teleport(serverPlayer, targetLevel, vec3, yaw, pitch);

// using a BlockPos (centers on block)
DimensionTeleport.teleport(serverPlayer, targetLevel, blockPos);
```

Handles same-dimension teleport (`teleportTo(x,y,z)`) vs cross-dimension (`teleportTo(level,x,y,z,yaw,pitch)`). Minecraft's API is different for each case and this smooths over it.

## FeatureHelper

```java
// place a feature at a position if the biome allows it
boolean ok = FeatureHelper.place(level, pos, SomeFeature.CONFIGURED_FEATURE);
```

Returns false if placement failed (wrong biome, obstruction, etc.).

## Notes

- `DimensionTeleport` is server-side only. It will crash if called client-side.
- Always check `!level.isClientSide()` before teleporting.
