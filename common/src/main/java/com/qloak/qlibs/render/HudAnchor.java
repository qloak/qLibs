package com.qloak.qlibs.render;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public final class HudAnchor {
    public enum Corner { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_CENTER, BOTTOM_CENTER }

    private final Corner corner;
    private final int padX, padY;

    public HudAnchor(@NotNull Corner corner, int padX, int padY) {
        this.corner = corner;
        this.padX = padX;
        this.padY = padY;
    }

    public int resolveX(int w) {
        int sw = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        return switch (corner) {
            case TOP_LEFT, BOTTOM_LEFT -> padX;
            case TOP_RIGHT, BOTTOM_RIGHT -> sw - w - padX;
            case TOP_CENTER, BOTTOM_CENTER -> (sw - w) / 2 + padX;
        };
    }

    public int resolveY(int h) {
        int sh = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        return switch (corner) {
            case TOP_LEFT, TOP_RIGHT, TOP_CENTER -> padY;
            case BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER -> sh - h - padY;
        };
    }

    public static HudAnchor topLeft(int x, int y) { return new HudAnchor(Corner.TOP_LEFT, x, y); }
    public static HudAnchor topRight(int x, int y) { return new HudAnchor(Corner.TOP_RIGHT, x, y); }
    public static HudAnchor bottomLeft(int x, int y) { return new HudAnchor(Corner.BOTTOM_LEFT, x, y); }
    public static HudAnchor bottomRight(int x, int y) { return new HudAnchor(Corner.BOTTOM_RIGHT, x, y); }
    public static HudAnchor topCenter(int x, int y) { return new HudAnchor(Corner.TOP_CENTER, x, y); }
}
