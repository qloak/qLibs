package com.qloak.qlibs.core.meta;

import com.qloak.qlibs.core.platform.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Runtime mod-compatibility registry.
 * Detects other mods at startup and allows conditional feature registration.
 */
public final class ModCompat {
    private static final Map<String, Boolean> CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Consumer<Boolean>> LISTENERS = new ConcurrentHashMap<>();

    public static void scan() {
        // Lazily evaluated on first query.
    }

    public static boolean isLoaded(@NotNull String modId) {
        return CACHE.computeIfAbsent(modId, Platform::isModLoaded);
    }

    /**
     * Register a callback invoked (immediately, if already known) when a mod's presence is detected.
     */
    public static void onDetect(@NotNull String modId, @NotNull Consumer<Boolean> callback) {
        LISTENERS.put(modId, callback);
        callback.accept(isLoaded(modId));
    }

    public static void whenLoaded(@NotNull String modId, @NotNull Runnable action) {
        if (isLoaded(modId)) {
            action.run();
        } else {
            LISTENERS.put(modId, present -> {
                if (Boolean.TRUE.equals(present)) action.run();
            });
        }
    }

    private ModCompat() {}
}
