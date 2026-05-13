package com.qloak.qlibs.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight in-memory registry for dynamic datapack-driven objects.
 */
public final class DynamicRegistry<T> {
    private final ResourceKey<Registry<T>> key;
    private final Map<Identifier, T> entries = new ConcurrentHashMap<>();

    public DynamicRegistry(@NotNull ResourceKey<Registry<T>> key) {
        this.key = key;
    }

    public void register(@NotNull Identifier id, @NotNull T entry) {
        entries.put(id, entry);
    }

    public T get(@NotNull Identifier id) {
        return entries.get(id);
    }

    public Map<Identifier, T> getEntries() {
        return Map.copyOf(entries);
    }

    public @NotNull ResourceKey<Registry<T>> key() {
        return key;
    }
}
