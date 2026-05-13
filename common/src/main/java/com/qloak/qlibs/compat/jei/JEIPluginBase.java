package com.qloak.qlibs.compat.jei;

import com.qloak.qlibs.QLibs;
import com.qloak.qlibs.core.meta.ModCompat;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for optional JEI integration. Subclass and annotate with @JeiPlugin.
 * Guarded by runtime mod detection so the class can be safely referenced even when JEI is absent.
 */
public abstract class JEIPluginBase {

    public static boolean isJEIPresent() {
        return ModCompat.isLoaded("jei");
    }

    public static boolean isREIPresent() {
        return ModCompat.isLoaded("roughlyenoughitems");
    }

    public static boolean isEMIPresent() {
        return ModCompat.isLoaded("emi");
    }

    /**
     * Any plugin should call this in its constructor or registration methods.
     */
    public static void logRegistration(@NotNull String category) {
        QLibs.LOGGER.info("Registering {} recipe plugin", category);
    }
}
