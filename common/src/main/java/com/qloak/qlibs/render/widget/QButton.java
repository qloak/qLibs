package com.qloak.qlibs.render.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class QButton implements QWidget {
    private int x, y, w, h;
    private Component text;
    private boolean enabled = true;
    private boolean hovered = false;
    private @Nullable Consumer<QButton> click;

    public QButton(int x, int y, int w, int h, @NotNull Component text) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.text = text;
    }

    public QButton onClick(@Nullable Consumer<QButton> c) { this.click = c; return this; }
    public void setEnabled(boolean v) { this.enabled = v; }
    public boolean isEnabled() { return enabled; }

    @Override public int x() { return x; }
    @Override public int y() { return y; }
    @Override public int width() { return w; }
    @Override public int height() { return h; }
    @Override public void setPosition(int x, int y) { this.x = x; this.y = y; }
    @Override public void setSize(int w, int h) { this.w = w; this.h = h; }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float delta) {
        hovered = isMouseOver(mx, my);
        int c = enabled ? (hovered ? 0xFFAAAAAA : 0xFF555555) : 0xFF333333;
        g.fill(x, y, x + w, y + h, c);
        g.fill(x + 1, y + 1, x + w - 1, y + h - 1, enabled ? 0xFF222222 : 0xFF111111);
        int tc = enabled ? 0xFFFFFFFF : 0xFF888888;
        int ty = y + (h - 8) / 2;
        g.drawCenteredString(Minecraft.getInstance().font, text, x + w / 2, ty, tc);
    }

    @Override
    public boolean mouseClicked(int mx, int my, int btn) {
        if (enabled && isMouseOver(mx, my) && btn == 0 && click != null) {
            click.accept(this);
            return true;
        }
        return false;
    }

    @Override public boolean mouseReleased(int mx, int my, int btn) { return false; }
    @Override public boolean mouseDragged(int mx, int my, int btn, double dx, double dy) { return false; }
    @Override public boolean keyPressed(int key, int scan, int mods) { return false; }
    @Override public boolean charTyped(char c, int mods) { return false; }
}
