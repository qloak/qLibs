# qMath

Vectors and easing. Bridges to Minecraft types where needed.

## Vec3

```java
Vec3 a = new Vec3(1, 2, 3);
Vec3 b = a.add(new Vec3(4, 5, 6));
Vec3 c = a.scale(2).normalize();
float d = a.dot(b);

// lerp
Vec3 mid = a.lerp(b, 0.5f);

// bridge to Minecraft
net.minecraft.world.phys.Vec3 mc = a.toMinecraft();
Vec3 back = Vec3.fromMinecraft(mc);
```

Immutable record. `ZERO` constant included.

## Easing

```java
float t = Easing.easeInOutCubic(progress); // 0..1 -> smoothed 0..1
float t2 = Easing.easeOutBack(progress);   // overshoot
```

Useful for UI animations or interpolated movement. Static methods, no allocation.
