package com.qloak.qlibs.render;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Cross-platform render utilities.
 */
public final class RenderHelper {

    public static void blit(@NotNull net.minecraft.client.gui.GuiGraphics graphics, @NotNull ResourceLocation texture,
                            int x, int y, int u, int v, int width, int height) {
        graphics.blit(texture, x, y, u, v, width, height);
    }

    private RenderHelper() {}
}
