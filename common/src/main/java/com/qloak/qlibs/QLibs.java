package com.qloak.qlibs;

import com.qloak.qlibs.core.logging.QLog;
import com.qloak.qlibs.core.meta.ModCompat;
import com.qloak.qlibs.core.platform.Platform;
import com.qloak.qlibs.core.scheduling.Scheduler;

public final class QLibs {
    public static final String MOD_ID = "qlibs";
    public static final QLog LOGGER = QLog.create(MOD_ID);

    private static boolean init = false;

    public static void init() {
        if (init) {
            LOGGER.warn("already init, skipping");
            return;
        }
        init = true;

        LOGGER.info("qLibs {} on {}", Platform.getModVersion(MOD_ID), Platform.getLoaderName());
        Scheduler.init();
        ModCompat.scan();
    }

    public static void clientInit() {
        LOGGER.debug("client init done");
    }

    private QLibs() {}
}
