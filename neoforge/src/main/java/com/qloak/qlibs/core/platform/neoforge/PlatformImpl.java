package com.qloak.qlibs.core.platform.neoforge;

import com.qloak.qlibs.core.platform.ModLoader;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class PlatformImpl {

    public static boolean isModLoaded(@NotNull String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.isProduction();
    }

    @NotNull
    public static String getLoaderName() {
        return ModLoader.NEOFORGE.name().toLowerCase();
    }

    @NotNull
    public static String getModVersion(@NotNull String modId) {
        return ModList.get().getModContainerById(modId)
                .map(c -> c.getModInfo().getVersion().toString())
                .orElse("unknown");
    }

    public static boolean isClient() {
        return FMLEnvironment.getDist().isClient();
    }

    public static boolean isServer() {
        return FMLEnvironment.getDist().isDedicatedServer();
    }

    @NotNull
    public static java.nio.file.Path getConfigDir() {
        return FMLLoader.getCurrent().getGameDir().resolve("config");
    }
}
