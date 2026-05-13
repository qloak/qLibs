package com.qloak.qlibs.data.datapack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities for interacting with datapacks at runtime.
 */
public final class DatapackHelper {

    public static boolean resourceExists(@NotNull ResourceManager manager, @NotNull ResourceLocation id) {
        return manager.getResource(id).isPresent();
    }

    public static boolean reloadableResourceExists(@NotNull MinecraftServer server, @NotNull ResourceLocation id) {
        return server.getResourceManager().getResource(id).isPresent();
    }

    private DatapackHelper() {}
}
