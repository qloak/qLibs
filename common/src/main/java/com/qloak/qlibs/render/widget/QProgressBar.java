package com.qloak.qlibs.render.widget;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public final class QProgressBar implements QWidget {
    private int x, y, w = 100, h = 10;
    private float progress = 0f;
    private int bg = 0xFF333333;
    private int fg = 0xFF00AA00;

    public QProgressBar(int x, int y, int w, int h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public QProgressBar progress(float p) { this.progress = Math.clamp(p, 0f, 1f); return this; }
    public QProgressBar bgColor(int c) { this.bg = c; return this; }
    public QProgressBar fgColor(int c) { this.fg = c; return this; }

    @Override public int x() { return x; }
    @Override public int y() { return y; }
    @Override public int width() { return w; }
    @Override public int height() { return h; }
    @Override public void setPosition(int x, int y) { this.x = x; this.y = y; }
    @Override public void setSize(int width, int height) { this.w = width; this.h = height; }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float delta) {
        g.fill(x, y, x + w, y + h, bg);
        int fw = (int) (w * progress);
        if (fw > 0) g.fill(x, y, x + fw, y + h, fg);
    }

    @Override public boolean mouseClicked(int mx, int my, int btn) { return false; }
    @Override public boolean mouseReleased(int mx, int my, int btn) { return false; }
    @Override public boolean mouseDragged(int mx, int my, int btn, double dx, double dy) { return false; }
    @Override public boolean keyPressed(int key, int scan, int mods) { return false; }
    @Override public boolean charTyped(char c, int mods) { return false; }
}
