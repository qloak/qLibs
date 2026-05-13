package com.qloak.qlibs.data.conditions;

import com.google.gson.JsonObject;
import com.qloak.qlibs.core.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Helpers for Fabric/NeoForge conditional resource loading.
 */
public final class ResourceConditions {

    public static final String MOD_LOADED = "qlibs:mod_loaded";
    public static final String PLATFORM = "qlibs:platform";

    /**
     * Fabric-style condition object builder.
     */
    @NotNull
    public static JsonObject modLoaded(@NotNull String modId) {
        JsonObject obj = new JsonObject();
        obj.addProperty("condition", MOD_LOADED);
        obj.addProperty("mod_id", modId);
        return obj;
    }

    @NotNull
    public static JsonObject platformIs(@NotNull String loader) {
        JsonObject obj = new JsonObject();
        obj.addProperty("condition", PLATFORM);
        obj.addProperty("loader", loader);
        return obj;
    }

    /**
     * Evaluates a qLibs condition object. Call this from your platform-specific condition handler.
     */
    public static boolean evaluate(@NotNull JsonObject condition) {
        String type = condition.get("condition").getAsString();
        return switch (type) {
            case MOD_LOADED -> Platform.isModLoaded(condition.get("mod_id").getAsString());
            case PLATFORM -> Platform.getLoaderName().equalsIgnoreCase(condition.get("loader").getAsString());
            default -> true;
        };
    }

    private ResourceConditions() {}
}
