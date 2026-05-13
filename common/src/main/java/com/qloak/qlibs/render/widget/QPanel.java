package com.qloak.qlibs.render.widget;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QPanel implements QWidget {
    private int x, y, w, h;
    private final List<QWidget> children = new ArrayList<>();
    private int bg = 0xFF111111;

    public QPanel(int x, int y, int w, int h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public QPanel background(int c) { this.bg = c; return this; }
    public void addChild(@NotNull QWidget c) { children.add(c); }
    public void removeChild(@NotNull QWidget c) { children.remove(c); }
    public List<QWidget> children() { return List.copyOf(children); }

    @Override public int x() { return x; }
    @Override public int y() { return y; }
    @Override public int width() { return w; }
    @Override public int height() { return h; }
    @Override public void setPosition(int x, int y) { this.x = x; this.y = y; }
    @Override public void setSize(int w, int h) { this.w = w; this.h = h; }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float delta) {
        g.fill(x, y, x + w, y + h, bg);
        // push pose so children draw relative to panel origin
        g.pose().pushPose();
        g.pose().translate(x, y, 0);
        for (QWidget c : children) c.render(g, mx - x, my - y, delta);
        g.pose().popPose();
    }

    @Override public boolean mouseClicked(int mx, int my, int btn) {
        int rx = mx - x, ry = my - y;
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).mouseClicked(rx, ry, btn)) return true;
        }
        return false;
    }

    @Override public boolean mouseReleased(int mx, int my, int btn) {
        int rx = mx - x, ry = my - y;
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).mouseReleased(rx, ry, btn)) return true;
        }
        return false;
    }

    @Override public boolean mouseDragged(int mx, int my, int btn, double dx, double dy) {
        int rx = mx - x, ry = my - y;
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).mouseDragged(rx, ry, btn, dx, dy)) return true;
        }
        return false;
    }

    @Override public boolean keyPressed(int key, int scan, int mods) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).keyPressed(key, scan, mods)) return true;
        }
        return false;
    }

    @Override public boolean charTyped(char c, int mods) {
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).charTyped(c, mods)) return true;
        }
        return false;
    }
}
