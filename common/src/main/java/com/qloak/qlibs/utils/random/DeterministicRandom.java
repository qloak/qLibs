package com.qloak.qlibs.utils.random;

import java.util.Random;

/**
 * Seeded random for deterministic worldgen / simulation behaviour.
 */
public final class DeterministicRandom extends Random {
    private final long seed;

    public DeterministicRandom(long seed) {
        super(seed);
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public float nextFloat(float min, float max) {
        return min + nextFloat() * (max - min);
    }

    public int nextInt(int min, int max) {
        return min + nextInt(max - min);
    }

    public boolean chance(float probability) {
        return nextFloat() < probability;
    }
}
