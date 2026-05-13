package com.qloak.qlibs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.qloak.qlibs.QLibs;
import com.qloak.qlibs.core.platform.Platform;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigFile {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load(@NotNull ModConfig config) {
        Path file = Platform.getConfigDir().resolve(config.modId() + ".json");
        if (!Files.exists(file)) return;

        try {
            String text = Files.readString(file);
            JsonObject root = GSON.fromJson(text, JsonObject.class);
            if (root == null) return;

            for (ConfigCategory cat : config.categories()) {
                JsonObject catObj = root.getAsJsonObject(cat.name());
                if (catObj == null) continue;
                for (ConfigValue<?> val : cat.values()) {
                    JsonElement el = catObj.get(val.key());
                    if (el == null || el.isJsonNull()) continue;
                    setFromJson(config, val.key(), el);
                }
            }
        } catch (Exception e) {
            QLibs.LOGGER.error("failed to load config for {}", config.modId(), e);
        }
    }

    public static void save(@NotNull ModConfig config) {
        Path file = Platform.getConfigDir().resolve(config.modId() + ".json");
        JsonObject root = new JsonObject();

        for (ConfigCategory cat : config.categories()) {
            JsonObject catObj = new JsonObject();
            for (ConfigValue<?> val : cat.values()) {
                catObj.add(val.key(), toJson(val.get()));
            }
            root.add(cat.name(), catObj);
        }

        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(root));
        } catch (IOException e) {
            QLibs.LOGGER.error("failed to save config for {}", config.modId(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void setFromJson(ModConfig config, String key, JsonElement el) {
        ConfigValue<T> val = (ConfigValue<T>) config.values().get(key);
        if (val == null) return;
        T parsed = parseJson(el, val.type());
        if (parsed != null) val.set(parsed);
    }

    @SuppressWarnings("unchecked")
    private static <T> T parseJson(JsonElement el, Class<T> type) {
        try {
            if (type == Integer.class || type == int.class) return (T) Integer.valueOf(el.getAsInt());
            if (type == Double.class || type == double.class) return (T) Double.valueOf(el.getAsDouble());
            if (type == Float.class || type == float.class) return (T) Float.valueOf(el.getAsFloat());
            if (type == Boolean.class || type == boolean.class) return (T) Boolean.valueOf(el.getAsBoolean());
            if (type == String.class) return (T) el.getAsString();
            if (type == Long.class || type == long.class) return (T) Long.valueOf(el.getAsLong());
        } catch (Exception e) {
            QLibs.LOGGER.warn("bad config value: {}", el);
        }
        return null;
    }

    private static JsonElement toJson(Object value) {
        if (value instanceof Integer v) return new JsonPrimitive(v);
        if (value instanceof Double v) return new JsonPrimitive(v);
        if (value instanceof Float v) return new JsonPrimitive(v);
        if (value instanceof Boolean v) return new JsonPrimitive(v);
        if (value instanceof String v) return new JsonPrimitive(v);
        if (value instanceof Long v) return new JsonPrimitive(v);
        return new JsonPrimitive(value.toString());
    }

    private ConfigFile() {}
}
