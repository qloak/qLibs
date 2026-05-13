package com.qloak.qlibs.math;

import org.jetbrains.annotations.NotNull;

// tiny immutable vec, mostly for rendering stuff
public record Vec3(float x, float y, float z) {
    public static final Vec3 ZERO = new Vec3(0, 0, 0);

    public Vec3 add(Vec3 o) { return new Vec3(x + o.x, y + o.y, z + o.z); }
    public Vec3 sub(Vec3 o) { return new Vec3(x - o.x, y - o.y, z - o.z); }
    public Vec3 scale(float s) { return new Vec3(x * s, y * s, z * s); }

    public float lengthSquared() { return x * x + y * y + z * z; }
    public float length() { return (float) Math.sqrt(lengthSquared()); }

    public Vec3 normalize() {
        float len = length();
        return len == 0 ? ZERO : scale(1f / len);
    }

    public float dot(Vec3 o) { return x * o.x + y * o.y + z * o.z; }

    public Vec3 lerp(Vec3 target, float t) {
        return new Vec3(
                x + (target.x - x) * t,
                y + (target.y - y) * t,
                z + (target.z - z) * t
        );
    }

    @NotNull
    public net.minecraft.world.phys.Vec3 toMinecraft() {
        return new net.minecraft.world.phys.Vec3(x, y, z);
    }

    @NotNull
    public static Vec3 fromMinecraft(@NotNull net.minecraft.world.phys.Vec3 v) {
        return new Vec3((float) v.x, (float) v.y, (float) v.z);
    }
}
