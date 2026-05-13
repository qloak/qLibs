package com.qloak.qlibs.core.platform;

/**
 * Identifies which mod loader is currently active.
 */
public enum ModLoader {
    FABRIC,
    NEOFORGE,
    UNKNOWN;

    public boolean isFabric() {
        return this == FABRIC;
    }

    public boolean isForgeLike() {
        return this == NEOFORGE;
    }
}
