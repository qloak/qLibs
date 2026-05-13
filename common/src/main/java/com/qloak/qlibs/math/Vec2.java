package com.qloak.qlibs.math;

/**
 * Immutable 2D float vector with Minecraft-optimized operations.
 */
public record Vec2(float x, float y) {
    public static final Vec2 ZERO = new Vec2(0, 0);
    public static final Vec2 ONE = new Vec2(1, 1);

    public Vec2 add(Vec2 o) {
        return new Vec2(x + o.x, y + o.y);
    }

    public Vec2 sub(Vec2 o) {
        return new Vec2(x - o.x, y - o.y);
    }

    public Vec2 scale(float s) {
        return new Vec2(x * s, y * s);
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vec2 normalize() {
        float len = length();
        return len == 0 ? ZERO : scale(1f / len);
    }

    public float dot(Vec2 o) {
        return x * o.x + y * o.y;
    }

    public Vec2 lerp(Vec2 target, float t) {
        return new Vec2(x + (target.x - x) * t, y + (target.y - y) * t);
    }
}
