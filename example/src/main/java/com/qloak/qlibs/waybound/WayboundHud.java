package com.qloak.qlibs.waybound;

import com.qloak.qlibs.render.HudAnchor;
import com.qloak.qlibs.render.HudOverlay;
import com.qloak.qlibs.render.widget.QLabel;
import com.qloak.qlibs.render.widget.QPanel;
import com.qloak.qlibs.render.widget.QProgressBar;
import com.qloak.qlibs.data.storage.PlayerDataStore;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;

public final class WayboundHud {
    private static final DecimalFormat FMT = new DecimalFormat("0.0");

    public static void register() {
        HudOverlay.register((g, delta) -> {
            if (!WayboundConfig.hud.get()) return;
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            Player p = mc.player;
            ItemStack main = p.getMainHandItem();
            ItemStack off = p.getOffhandItem();
            ItemStack compass = main.is(WayboundItems.WAYBOUND_COMPASS) ? main :
                                (off.is(WayboundItems.WAYBOUND_COMPASS) ? off : ItemStack.EMPTY);

            if (compass.isEmpty() || !WayboundItems.WayboundCompass.isBound(compass)) return;

            String dim = WayboundItems.WayboundCompass.getDimension(compass);
            double tx = WayboundItems.WayboundCompass.getX(compass);
            double ty = WayboundItems.WayboundCompass.getY(compass);
            double tz = WayboundItems.WayboundCompass.getZ(compass);

            boolean same = p.level().dimension().location().toString().equals(dim);
            Vec3 pos = p.position();
            double dist = same ? pos.distanceTo(new Vec3(tx, ty, tz)) : Double.NaN;

            long now = System.currentTimeMillis();
            long cd = WayboundConfig.cdSec.get() * 1000L;
            long last = PlayerDataStore.getLong(p, "waybound:last_teleport", 0);
            float prog = 1f;
            if (now - last < cd) prog = (float) (now - last) / (float) cd;

            String name = ResourceLocation.tryParse(dim).getPath();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            QPanel panel = new QPanel(0, 0, 180, 72).background(0xCC000000);
            panel.addChild(new QLabel(5, 4, Component.literal("Waybound Compass")).color(0xFFAA55));
            panel.addChild(new QLabel(5, 16, Component.literal("Target: " + name)).color(0xFFFFFF));
            panel.addChild(new QLabel(5, 28, Component.literal(FMT.format(tx) + ", " + FMT.format(ty) + ", " + FMT.format(tz))).color(0xCCCCCC));
            panel.addChild(new QLabel(5, 40, Component.literal(same ? "Distance: " + FMT.format(dist) + "m" : "Different dimension")).color(0xAAAAAA));
            panel.addChild(new QLabel(5, 54, Component.literal("Cooldown")).color(0xAAAAAA));
            panel.addChild(new QProgressBar(65, 55, 110, 7).progress(prog).fgColor(prog >= 1f ? 0xFF00AA00 : 0xFFAA5500));

            HudAnchor a = HudAnchor.topRight(8, 8);
            panel.setPosition(a.resolveX(panel.width()), a.resolveY(panel.height()));
            panel.render(g, 0, 0, 0);
        });
    }
}
