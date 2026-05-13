package com.qloak.qlibs.render.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class ParticleHelper {

    public static void spawn(@NotNull Level level, @NotNull ParticleOptions type,
                              double x, double y, double z,
                              double vx, double vy, double vz, int count) {
        if (level instanceof ServerLevel s) {
            s.sendParticles(type, x, y, z, count, vx, vy, vz, 0.0);
        } else {
            level.addParticle(type, x, y, z, vx, vy, vz);
        }
    }

    public static void spawnBurst(@NotNull Level level, @NotNull ParticleOptions type,
                                   @NotNull Vec3 center, double spread, int count) {
        for (int i = 0; i < count; i++) {
            double vx = (Math.random() - 0.5) * spread;
            double vy = (Math.random() - 0.5) * spread;
            double vz = (Math.random() - 0.5) * spread;
            level.addParticle(type, center.x, center.y, center.z, vx, vy, vz);
        }
    }

    public static void spawnSphere(@NotNull Level level, @NotNull ParticleOptions type,
                                    @NotNull Vec3 center, double radius, int count) {
        for (int i = 0; i < count; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double phi = Math.acos(2 * Math.random() - 1);
            double x = center.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.y + radius * Math.sin(phi) * Math.sin(theta);
            double z = center.z + radius * Math.cos(phi);
            level.addParticle(type, x, y, z, 0, 0, 0);
        }
    }

    private ParticleHelper() {}
}
