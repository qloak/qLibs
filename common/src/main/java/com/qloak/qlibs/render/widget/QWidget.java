package com.qloak.qlibs.render.widget;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public interface QWidget {
    int x();
    int y();
    int width();
    int height();

    void setPosition(int x, int y);
    void setSize(int width, int height);

    void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick);
    boolean mouseClicked(int mouseX, int mouseY, int button);
    boolean mouseReleased(int mouseX, int mouseY, int button);
    boolean mouseDragged(int mouseX, int mouseY, int button, double dragX, double dragY);
    boolean keyPressed(int keyCode, int scanCode, int modifiers);
    boolean charTyped(char codePoint, int modifiers);

    default boolean isMouseOver(int mx, int my) {
        return mx >= x() && mx < x() + width() && my >= y() && my < y() + height();
    }
}
