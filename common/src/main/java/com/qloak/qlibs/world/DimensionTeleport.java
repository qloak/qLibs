package com.qloak.qlibs.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class DimensionTeleport {

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target,
                                    double x, double y, double z, float yaw, float pitch) {
        if (p.level() == target) {
            p.teleportTo(x, y, z);
            p.setYRot(yaw);
            p.setXRot(pitch);
        } else {
            p.teleportTo(target, x, y, z, yaw, pitch);
        }
    }

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target,
                                    @NotNull Vec3 pos, float yaw, float pitch) {
        teleport(p, target, pos.x, pos.y, pos.z, yaw, pitch);
    }

    public static void teleport(@NotNull ServerPlayer p, @NotNull ServerLevel target, @NotNull BlockPos pos) {
        teleport(p, target, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, p.getYRot(), p.getXRot());
    }

    private DimensionTeleport() {}
}
