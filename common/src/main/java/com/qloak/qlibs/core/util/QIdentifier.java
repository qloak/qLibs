package com.qloak.qlibs.core.util;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Identifier helpers.
 */
public final class QIdentifier {

    @NotNull
    public static Identifier of(@NotNull String modId, @NotNull String path) {
        return Identifier.fromNamespaceAndPath(modId, path);
    }

    @NotNull
    public static Identifier qlibs(@NotNull String path) {
        return Identifier.fromNamespaceAndPath("qlibs", path);
    }

    @NotNull
    public static Identifier mc(@NotNull String path) {
        return Identifier.withDefaultNamespace(path);
    }

    private QIdentifier() {}
}
