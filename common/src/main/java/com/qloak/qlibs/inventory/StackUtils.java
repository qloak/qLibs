package com.qloak.qlibs.inventory;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * ItemStack helpers for everyday container logic.
 */
public final class StackUtils {

    public static boolean canMerge(@NotNull ItemStack a, @NotNull ItemStack b) {
        return ItemStack.isSameItemSameComponents(a, b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
    }

    @NotNull
    public static ItemStack merge(@NotNull ItemStack into, @NotNull ItemStack from) {
        if (!canMerge(into, from)) return into;
        int transfer = Math.min(from.getCount(), into.getMaxStackSize() - into.getCount());
        into.grow(transfer);
        from.shrink(transfer);
        return into;
    }

    public static boolean isEmptyOrNull(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }

    private StackUtils() {}
}
