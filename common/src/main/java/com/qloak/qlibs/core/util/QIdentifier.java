package com.qloak.qlibs.core.util;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * ResourceLocation helpers.
 */
public final class QIdentifier {

    @NotNull
    public static ResourceLocation of(@NotNull String modId, @NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }

    @NotNull
    public static ResourceLocation qlibs(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath("qlibs", path);
    }

    @NotNull
    public static ResourceLocation mc(@NotNull String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    private QIdentifier() {}
}
