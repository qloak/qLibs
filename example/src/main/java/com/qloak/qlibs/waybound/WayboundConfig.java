package com.qloak.qlibs.waybound;

import com.qloak.qlibs.config.ConfigBuilder;
import com.qloak.qlibs.config.ConfigValue;
import com.qloak.qlibs.config.ModConfig;
import com.qloak.qlibs.config.QConfig;

public final class WayboundConfig {
    public static final ModConfig CONFIG;
    public static final ConfigValue<Integer> costXp;
    public static final ConfigValue<Integer> cdSec;
    public static final ConfigValue<Boolean> crossDim;
    public static final ConfigValue<Boolean> hud;

    static {
        ConfigBuilder b = ConfigBuilder.create(WayboundMod.MOD_ID)
                .category("general", "waybound settings");
        b.defineInt("teleport_cost_xp", 2);
        b.defineInt("teleport_cooldown_seconds", 30);
        b.defineBool("allow_cross_dimension", true);
        b.defineBool("hud_enabled", true);
        CONFIG = b.build();
        QConfig.register(CONFIG);

        costXp = (ConfigValue<Integer>) CONFIG.values().get("teleport_cost_xp");
        cdSec = (ConfigValue<Integer>) CONFIG.values().get("teleport_cooldown_seconds");
        crossDim = (ConfigValue<Boolean>) CONFIG.values().get("allow_cross_dimension");
        hud = (ConfigValue<Boolean>) CONFIG.values().get("hud_enabled");
    }

    public static void init() {
        WayboundMod.LOGGER.info("config: cost={}xp cd={}s crossDim={} hud={}",
                costXp.get(), cdSec.get(), crossDim.get(), hud.get());
    }
}
