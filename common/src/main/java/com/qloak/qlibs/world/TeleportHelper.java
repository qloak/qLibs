package com.qloak.qlibs.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Safe cross-dimensional teleport utilities.
 */
public final class TeleportHelper {

    public static void teleport(@NotNull Entity entity, @NotNull ServerLevel targetLevel,
                                 double x, double y, double z, float yaw, float pitch) {
        if (entity instanceof ServerPlayer player) {
            player.teleportTo(targetLevel, x, y, z, java.util.Collections.emptySet(), yaw, pitch);
        } else {
            entity.teleportTo(x, y, z);
            if (entity.level() != targetLevel) {
                entity.changeDimension(new net.minecraft.world.level.portal.DimensionTransition(
                        targetLevel,
                        new net.minecraft.world.phys.Vec3(x, y, z),
                        entity.getDeltaMovement(),
                        yaw, pitch,
                        net.minecraft.world.level.portal.DimensionTransition.DO_NOTHING
                ));
            }
        }
    }

    public static void teleport(@NotNull Entity entity, @NotNull ServerLevel targetLevel, @NotNull BlockPos pos) {
        teleport(entity, targetLevel, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                entity.getYRot(), entity.getXRot());
    }

    private TeleportHelper() {}
}
