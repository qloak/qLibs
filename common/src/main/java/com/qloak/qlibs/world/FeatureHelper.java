package com.qloak.qlibs.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Helpers for feature placement and structure generation.
 */
public final class FeatureHelper {

    public static boolean isSolidBelow(@NotNull LevelAccessor level, @NotNull BlockPos pos, int distance) {
        for (int i = 1; i <= distance; i++) {
            if (level.getBlockState(pos.below(i)).isSolid()) return true;
        }
        return false;
    }

    public static boolean isAirCube(@NotNull LevelAccessor level, @NotNull BlockPos pos, int width, int height, int depth) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (!level.getBlockState(pos.offset(x, y, z)).isAir()) return false;
                }
            }
        }
        return true;
    }

    private FeatureHelper() {}
}
