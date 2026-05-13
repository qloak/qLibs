package com.qloak.qlibs.waybound;

import com.qloak.qlibs.core.platform.Platform;
import com.qloak.qlibs.render.HudOverlay;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WayboundMod implements ModInitializer {
    public static final String MOD_ID = "waybound";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("waybound on {}", Platform.getLoaderName());

        WayboundConfig.init();
        WayboundItems.init();
        WayboundNetworking.init();

        HudOverlay.init();
        WayboundHud.register();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(new ItemStack(WayboundItems.WAYBOUND_COMPASS));
        });

        LOGGER.info("waybound loaded");
    }
}
