package com.qloak.qlibs.core.platform.fabric;

import com.qloak.qlibs.core.platform.ModLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class PlatformImpl {

    public static boolean isModLoaded(@NotNull String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @NotNull
    public static String getLoaderName() {
        return ModLoader.FABRIC.name().toLowerCase();
    }

    @NotNull
    public static String getModVersion(@NotNull String modId) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(c -> c.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @NotNull
    public static java.nio.file.Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
