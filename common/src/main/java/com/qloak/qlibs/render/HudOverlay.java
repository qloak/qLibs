package com.qloak.qlibs.render;

import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class HudOverlay {
    private static final List<BiConsumer<GuiGraphics, DeltaTracker>> RENDERERS = new ArrayList<>();

    public static void register(@NotNull BiConsumer<GuiGraphics, DeltaTracker> r) {
        RENDERERS.add(r);
    }

    public static void init() {
        ClientGuiEvent.RENDER_HUD.register((g, delta) -> {
            for (var r : RENDERERS) r.accept(g, delta);
        });
    }

    private HudOverlay() {}
}
