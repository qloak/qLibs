package com.qloak.qlibs.world.biome;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

/**
 * Helpers for attaching features/structures to biomes.
 * Platform-specific registration hooks should call these during biome modification events.
 */
public final class BiomeModifierHelper {

    public static void addFeature(@NotNull Holder<Biome> biome, @NotNull GenerationStep.Decoration step,
                                   @NotNull Holder<PlacedFeature> feature) {
        // Consumers should call this from platform-specific biome modification events.
        // Fabric: BiomeModification API
        // NeoForge: BiomeModifier JSON or event
    }

    public static boolean isOverworld(@NotNull Holder<Biome> biome) {
        return biome.isBound() && !biome.value().hasPrecipitation();
    }

    public static boolean isOcean(@NotNull Holder<Biome> biome) {
        if (!biome.isBound()) return false;
        String name = biome.unwrapKey().map(k -> k.location().getPath()).orElse("");
        return name.contains("ocean");
    }

    private BiomeModifierHelper() {}
}
