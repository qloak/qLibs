package com.qloak.qlibs.compat;

import com.qloak.qlibs.core.meta.ModCompat;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Central soft-dependency helper. Features are only loaded when the target mod is present.
 */
public final class CompatRegistry {

    public static <T> T whenLoaded(@NotNull String modId, @NotNull Supplier<T> factory) {
        if (ModCompat.isLoaded(modId)) {
            return factory.get();
        }
        return null;
    }

    public static void runIfLoaded(@NotNull String modId, @NotNull Runnable action) {
        if (ModCompat.isLoaded(modId)) {
            action.run();
        }
    }

    private CompatRegistry() {}
}
