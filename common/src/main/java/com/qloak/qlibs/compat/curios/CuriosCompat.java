package com.qloak.qlibs.compat.curios;

import com.qloak.qlibs.core.meta.ModCompat;
import org.jetbrains.annotations.NotNull;

/**
 * Soft-dependency helper for Curios (NeoForge) / Trinkets (Fabric).
 * Use CuriosCompat.whenLoaded() to register slots or query equipped trinkets.
 */
public final class CuriosCompat {

    public static boolean isCuriosLoaded() {
        return ModCompat.isLoaded("curios");
    }

    public static boolean isTrinketsLoaded() {
        return ModCompat.isLoaded("trinkets");
    }

    public static boolean isAnyLoaded() {
        return isCuriosLoaded() || isTrinketsLoaded();
    }

    /**
     * Run an action only if Curios or Trinkets is present.
     */
    public static void whenLoaded(@NotNull Runnable action) {
        if (isAnyLoaded()) action.run();
    }

    private CuriosCompat() {}
}
