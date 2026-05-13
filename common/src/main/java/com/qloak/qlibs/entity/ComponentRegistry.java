package com.qloak.qlibs.entity;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Lightweight per-entity component attachment system.
 * This is a simple alternative to Cardinal Components / Forge Capabilities.
 */
public final class ComponentRegistry {
    private static final Map<Entity, Map<Class<?>, Object>> COMPONENTS = new IdentityHashMap<>();

    public static <T> void attach(@NotNull Entity entity, @NotNull Class<T> type, @NotNull T instance) {
        COMPONENTS.computeIfAbsent(entity, e -> new IdentityHashMap<>()).put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(@NotNull Entity entity, @NotNull Class<T> type) {
        Map<Class<?>, Object> map = COMPONENTS.get(entity);
        return map != null ? (T) map.get(type) : null;
    }

    public static <T> boolean has(@NotNull Entity entity, @NotNull Class<T> type) {
        Map<Class<?>, Object> map = COMPONENTS.get(entity);
        return map != null && map.containsKey(type);
    }

    public static <T> void remove(@NotNull Entity entity, @NotNull Class<T> type) {
        Map<Class<?>, Object> map = COMPONENTS.get(entity);
        if (map != null) map.remove(type);
    }

    public static void clear(@NotNull Entity entity) {
        COMPONENTS.remove(entity);
    }
}
