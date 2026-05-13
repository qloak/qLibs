package com.qloak.qlibs.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.qloak.qlibs.QLibs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Type-safe JSON resource loader with validation hooks.
 */
public final class JsonLoader<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>> {
    public static final Gson GSON = new GsonBuilder().setLenient().create();

    private final String directory;
    private final Class<T> type;
    private final Function<JsonElement, T> parser;
    private final Map<ResourceLocation, T> entries = new HashMap<>();

    public JsonLoader(@NotNull String directory, @NotNull Class<T> type,
                      @NotNull Function<JsonElement, T> parser) {
        this.directory = directory;
        this.type = type;
        this.parser = parser;
    }

    @Override
    protected @NotNull Map<ResourceLocation, T> prepare(@NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        Map<ResourceLocation, T> result = new HashMap<>();
        for (var entry : manager.listResources(directory, p -> p.getPath().endsWith(".json")).entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                JsonElement el = GSON.fromJson(reader, JsonElement.class);
                T value = parser.apply(el);
                if (value != null) result.put(entry.getKey(), value);
            } catch (Exception e) {
                QLibs.LOGGER.error("Failed to load JSON {}: {}", entry.getKey(), e.getMessage());
            }
        }
        return result;
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, T> prepared, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        entries.clear();
        entries.putAll(prepared);
    }

    public Map<ResourceLocation, T> getEntries() {
        return Map.copyOf(entries);
    }
}
