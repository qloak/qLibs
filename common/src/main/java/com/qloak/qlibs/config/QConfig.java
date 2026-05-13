package com.qloak.qlibs.config;

import com.qloak.qlibs.QLibs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class QConfig {
    private static final Map<String, ModConfig> REGISTRY = new HashMap<>();

    public static void register(@NotNull ModConfig cfg) {
        REGISTRY.put(cfg.modId(), cfg);
        ConfigFile.load(cfg);
        QLibs.LOGGER.debug("config registered for {}", cfg.modId());
    }

    public static ModConfig get(@NotNull String modId) {
        return REGISTRY.get(modId);
    }

    public static void forEach(@NotNull Consumer<ModConfig> action) {
        REGISTRY.values().forEach(action);
    }

    public static void save(@NotNull String modId) {
        ModConfig cfg = REGISTRY.get(modId);
        if (cfg != null) ConfigFile.save(cfg);
    }

    public static void saveAll() {
        REGISTRY.values().forEach(ConfigFile::save);
    }

    private QConfig() {}
}
