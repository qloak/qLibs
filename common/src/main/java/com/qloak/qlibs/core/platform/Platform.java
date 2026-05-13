package com.qloak.qlibs.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class Platform {

    @ExpectPlatform
    public static boolean isModLoaded(@NotNull String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevelopmentEnvironment() {
        throw new AssertionError();
    }

    @ExpectPlatform
    @NotNull
    public static String getLoaderName() {
        throw new AssertionError();
    }

    @ExpectPlatform
    @NotNull
    public static String getModVersion(@NotNull String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isClient() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    @NotNull
    public static java.nio.file.Path getConfigDir() {
        throw new AssertionError();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("qlibs", path);
    }

    private Platform() {}
}
