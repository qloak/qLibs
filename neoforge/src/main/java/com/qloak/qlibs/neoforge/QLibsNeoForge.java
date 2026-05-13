package com.qloak.qlibs.neoforge;

import com.qloak.qlibs.QLibs;
import com.qloak.qlibs.core.platform.Platform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(QLibs.MOD_ID)
public class QLibsNeoForge {
    public QLibsNeoForge(IEventBus eventBus) {
        QLibs.init();
        if (Platform.isClient()) {
            QLibs.clientInit();
        }
    }
}
