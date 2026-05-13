package com.qloak.qlibs.fabric;

import com.qloak.qlibs.QLibs;
import net.fabricmc.api.ClientModInitializer;

public class QLibsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        QLibs.clientInit();
    }
}
