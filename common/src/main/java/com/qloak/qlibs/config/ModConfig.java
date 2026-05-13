package com.qloak.qlibs.config;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface ModConfig {
    @NotNull String modId();
    @NotNull List<ConfigCategory> categories();
    @NotNull Map<String, ConfigValue<?>> values();

    @SuppressWarnings("unchecked")
    default <T> T get(@NotNull String key) {
        ConfigValue<?> v = values().get(key);
        if (v == null) throw new IllegalArgumentException("unknown key: " + key);
        return (T) v.get();
    }

    default boolean getBool(@NotNull String key) { return get(key); }
    default int getInt(@NotNull String key) { return get(key); }
    default double getDouble(@NotNull String key) { return get(key); }
    default String getString(@NotNull String key) { return get(key); }
}
