package com.qloak.qlibs.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Safe cross-dimensional teleport utilities.
 */
public final class TeleportHelper {

    public static void teleport(@NotNull Entity entity, @NotNull ServerLevel targetLevel,
                                 double x, double y, double z, float yaw, float pitch) {
        entity.teleportTo(targetLevel, x, y, z, Relative.ALL, yaw, pitch, false);
    }

    public static void teleport(@NotNull Entity entity, @NotNull ServerLevel targetLevel, @NotNull BlockPos pos) {
        teleport(entity, targetLevel, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                entity.getYRot(), entity.getXRot());
    }

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target,
                                double x, double y, double z, float yaw, float pitch) {
        p.teleportTo(target, x, y, z, Relative.ALL, yaw, pitch, false);
    }

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target,
                                @NotNull Vec3 pos, float yaw, float pitch) {
        teleport(p, target, pos.x, pos.y, pos.z, yaw, pitch);
    }

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target, @NotNull BlockPos pos) {
        teleport(p, target, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, p.getYRot(), p.getXRot());
    }

    private TeleportHelper() {}
}
