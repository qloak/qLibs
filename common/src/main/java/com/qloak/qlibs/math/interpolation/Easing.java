package com.qloak.qlibs.math.interpolation;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Common easing functions for animations and interpolation.
 */
public enum Easing {
    LINEAR(t -> t),
    EASE_IN_QUAD(t -> t * t),
    EASE_OUT_QUAD(t -> 1 - (1 - t) * (1 - t)),
    EASE_IN_OUT_QUAD(t -> t < 0.5 ? 2 * t * t : 1 - (float) Math.pow(-2 * t + 2, 2) / 2),
    EASE_IN_CUBIC(t -> t * t * t),
    EASE_OUT_CUBIC(t -> 1 - (float) Math.pow(1 - t, 3)),
    EASE_IN_OUT_CUBIC(t -> t < 0.5 ? 4 * t * t * t : 1 - (float) Math.pow(-2 * t + 2, 3) / 2),
    EASE_OUT_BACK(t -> {
        float c1 = 1.70158f;
        float c3 = c1 + 1;
        return 1 + c3 * (float) Math.pow(t - 1, 3) + c1 * (float) Math.pow(t - 1, 2);
    });

    private final Function<Float, Float> fn;

    Easing(@NotNull Function<Float, Float> fn) {
        this.fn = fn;
    }

    public float apply(float t) {
        return fn.apply(Math.clamp(t, 0f, 1f));
    }

    public float apply(float t, float from, float to) {
        return from + (to - from) * apply(t);
    }
}
