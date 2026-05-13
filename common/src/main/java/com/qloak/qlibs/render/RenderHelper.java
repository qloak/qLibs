package com.qloak.qlibs.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
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

    public static void blit(@NotNull GuiGraphics graphics, @NotNull ResourceLocation texture,
                            int x, int y, int u, int v, int width, int height) {
        graphics.blit(texture, x, y, u, v, width, height);
    }

    public static void bindTexture(@NotNull ResourceLocation texture) {
        RenderSystem.setShaderTexture(0, texture);
    }

    private RenderHelper() {}
}
