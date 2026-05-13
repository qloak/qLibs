package com.qloak.qlibs.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Entity spawning utilities.
 */
public final class SpawnHelper {

    public static <T extends Entity> T spawn(@NotNull ServerLevel level, @NotNull EntityType<T> type,
                                                @NotNull BlockPos pos, @NotNull EntitySpawnReason reason) {
        T entity = type.create(level, reason);
        if (entity != null) {
            entity.teleportSetPosition(new PositionMoveRotation(
                    new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5),
                    entity.getDeltaMovement(),
                    level.random.nextFloat() * 360f, 0f
            ), Set.of());
            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), reason, null);
            }
            level.addFreshEntity(entity);
        }
        return entity;
    }

    public static boolean isDarkEnoughForMonsterSpawn(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
        return level.getBrightness(LightLayer.BLOCK, pos) < 1
                && level.getBrightness(LightLayer.SKY, pos) < 8;
    }

    public static boolean hasValidFloor(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    private SpawnHelper() {}
}
