package com.qloak.qlibs.render.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class QLabel implements QWidget {
    private int x, y, w, h;
    private Component text = Component.empty();
    private int color = 0xFFFFFF;
    private boolean shadow = true;

    public QLabel(int x, int y, @NotNull Component text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public QLabel color(int c) { this.color = c; return this; }
    public QLabel shadow(boolean s) { this.shadow = s; return this; }
    public QLabel text(@NotNull Component t) { this.text = t; return this; }

    @Override public int x() { return x; }
    @Override public int y() { return y; }
    @Override public int width() { return w; }
    @Override public int height() { return h; }
    @Override public void setPosition(int x, int y) { this.x = x; this.y = y; }
    @Override public void setSize(int width, int height) { this.w = width; this.h = height; }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float delta) {
        g.drawString(net.minecraft.client.Minecraft.getInstance().font, text, x, y, color, shadow);
    }

    @Override public boolean mouseClicked(int mx, int my, int btn) { return false; }
    @Override public boolean mouseReleased(int mx, int my, int btn) { return false; }
    @Override public boolean mouseDragged(int mx, int my, int btn, double dx, double dy) { return false; }
    @Override public boolean keyPressed(int key, int scan, int mods) { return false; }
    @Override public boolean charTyped(char c, int mods) { return false; }
}
