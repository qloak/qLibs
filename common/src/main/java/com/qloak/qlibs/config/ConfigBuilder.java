package com.qloak.qlibs.config;

import com.qloak.qlibs.config.validation.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public final class ConfigBuilder {
    private final String modId;
    private final List<ConfigCategory> cats = new ArrayList<>();
    private final Map<String, ConfigValue<?>> flat = new LinkedHashMap<>();
    private String curCat = "general";
    private String curDesc = "";
    private final List<ConfigValue<?>> curVals = new ArrayList<>();

    private ConfigBuilder(@NotNull String modId) {
        this.modId = modId;
    }

    public static ConfigBuilder create(@NotNull String modId) {
        return new ConfigBuilder(modId);
    }

    public ConfigBuilder category(@NotNull String name) {
        flush();
        this.curCat = name;
        this.curDesc = "";
        return this;
    }

    public ConfigBuilder category(@NotNull String name, @NotNull String desc) {
        flush();
        this.curCat = name;
        this.curDesc = desc;
        return this;
    }

    public <T> ConfigBuilder define(@NotNull String key, T def, @NotNull Class<T> type) {
        return define(key, "", def, type, null, null);
    }

    public <T> ConfigBuilder define(@NotNull String key, @NotNull String comment, T def, @NotNull Class<T> type) {
        return define(key, comment, def, type, null, null);
    }

    public <T> ConfigBuilder define(@NotNull String key, T def, @NotNull Class<T> type, @NotNull Validator<T> v) {
        return define(key, "", def, type, v, null);
    }

    public <T> ConfigBuilder define(@NotNull String key, @NotNull String comment, T def, @NotNull Class<T> type,
                                     Validator<T> v, Consumer<T> reload) {
        if (flat.containsKey(key)) throw new IllegalArgumentException("duplicate key: " + key);
        ConfigValue<T> val = new ConfigValue<>(key, comment, def, type, v, reload);
        flat.put(key, val);
        curVals.add(val);
        return this;
    }

    public ConfigBuilder defineBool(@NotNull String key, boolean def) {
        return define(key, def, Boolean.class);
    }

    public ConfigBuilder defineBool(@NotNull String key, @NotNull String comment, boolean def) {
        return define(key, comment, def, Boolean.class);
    }

    public ConfigBuilder defineInt(@NotNull String key, int def) {
        return define(key, def, Integer.class);
    }

    public ConfigBuilder defineInt(@NotNull String key, @NotNull String comment, int def) {
        return define(key, comment, def, Integer.class);
    }

    public ConfigBuilder defineDouble(@NotNull String key, double def) {
        return define(key, def, Double.class);
    }

    public ConfigBuilder defineString(@NotNull String key, @NotNull String def) {
        return define(key, def, String.class);
    }

    public ModConfig build() {
        flush();
        return new BuiltConfig(modId, List.copyOf(cats), Map.copyOf(flat));
    }

    private void flush() {
        if (!curVals.isEmpty()) {
            cats.add(new ConfigCategory(curCat, curDesc, List.copyOf(curVals)));
            curVals.clear();
        }
    }

    private record BuiltConfig(@NotNull String modId,
                               @NotNull List<ConfigCategory> categories,
                               @NotNull Map<String, ConfigValue<?>> values) implements ModConfig {}
}
