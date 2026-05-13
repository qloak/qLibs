package com.qloak.qlibs.render;

import org.jetbrains.annotations.NotNull;

/**
 * Cross-platform render utilities (Sodium / Iris aware checks planned).
 */
public final class RenderHelper {
    private static Boolean sodiumLoaded;
    private static Boolean irisLoaded;

    public static boolean isSodiumLoaded() {
        if (sodiumLoaded == null) {
            sodiumLoaded = com.qloak.qlibs.core.platform.Platform.isModLoaded("sodium");
        }
        return sodiumLoaded;
    }

    public static boolean isIrisLoaded() {
        if (irisLoaded == null) {
            irisLoaded = com.qloak.qlibs.core.platform.Platform.isModLoaded("iris") ||
                         com.qloak.qlibs.core.platform.Platform.isModLoaded("oculus");
        }
        return irisLoaded;
    }

    private RenderHelper() {}
}
