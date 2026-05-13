package com.qloak.qlibs.world.noise;

/**
 * Deterministic simplex-like noise for worldgen utilities.
 */
public final class SimpleNoise {
    private final long seed;

    public SimpleNoise(long seed) {
        this.seed = seed;
    }

    public double sample(double x, double y) {
        long n = (long) (x * 374761393L + y * 668265263L + seed);
        n = (n ^ (n >>> 13)) * 1274126177L;
        n = n ^ (n >>> 16);
        return ((n & 0x7FFFFFFF) / (double) 0x7FFFFFFF) * 2.0 - 1.0;
    }

    public double sample(double x, double y, double z) {
        long n = (long) (x * 374761393L + y * 668265263L + z * 1212121L + seed);
        n = (n ^ (n >>> 13)) * 1274126177L;
        n = n ^ (n >>> 16);
        return ((n & 0x7FFFFFFF) / (double) 0x7FFFFFFF) * 2.0 - 1.0;
    }

    public double octave(double x, double y, int octaves, double persistence) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double max = 0;
        for (int i = 0; i < octaves; i++) {
            total += sample(x * frequency, y * frequency) * amplitude;
            max += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }
        return total / max;
    }
}
