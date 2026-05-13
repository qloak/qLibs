package com.qloak.qlibs.fabric;

import com.qloak.qlibs.QLibs;
import net.fabricmc.api.ModInitializer;

public class QLibsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        QLibs.init();
    }
}
