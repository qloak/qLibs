package com.qloak.qlibs.inventory.menu;

import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinate and slot-layout helpers for building container menus.
 * Actual slot addition must be done inside the menu subclass constructor
 * because AbstractContainerMenu.addSlot is protected.
 */
public final class MenuBuilder {

    public static final int SLOT_SIZE = 18;

    @NotNull
    public static List<SlotCoords> playerInventorySlots(int startX, int startY) {
        List<SlotCoords> list = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                list.add(new SlotCoords(col + row * 9 + 9, startX + col * SLOT_SIZE, startY + row * SLOT_SIZE));
            }
        }
        for (int col = 0; col < 9; col++) {
            list.add(new SlotCoords(col, startX + col * SLOT_SIZE, startY + 58));
        }
        return list;
    }

    @NotNull
    public static List<SlotCoords> gridSlots(int startIndex, int cols, int rows, int startX, int startY) {
        List<SlotCoords> list = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                list.add(new SlotCoords(startIndex + col + row * cols, startX + col * SLOT_SIZE, startY + row * SLOT_SIZE));
            }
        }
        return list;
    }

    /**
     * Describes where to place a slot so a menu constructor can create and add it.
     */
    public record SlotCoords(int index, int x, int y) {}

    private MenuBuilder() {}
}
