package com.qloak.qlibs.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight in-memory registry for dynamic datapack-driven objects.
 */
public final class DynamicRegistry<T> {
    private final ResourceKey<Registry<T>> key;
    private final Map<ResourceLocation, T> entries = new ConcurrentHashMap<>();

    public DynamicRegistry(@NotNull ResourceKey<Registry<T>> key) {
        this.key = key;
    }

    public void register(@NotNull ResourceLocation id, @NotNull T entry) {
        entries.put(id, entry);
    }

    public T get(@NotNull ResourceLocation id) {
        return entries.get(id);
    }

    public Map<ResourceLocation, T> getEntries() {
        return Map.copyOf(entries);
    }

    public @NotNull ResourceKey<Registry<T>> key() {
        return key;
    }
}
