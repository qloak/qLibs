package com.qloak.qlibs.utils.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

/**
 * Fluent text builder with gradient and action helpers.
 */
public final class TextHelper {

    @NotNull
    public static MutableComponent literal(@NotNull String text) {
        return Component.literal(text);
    }

    @NotNull
    public static MutableComponent translatable(@NotNull String key, Object... args) {
        return Component.translatable(key, args);
    }

    @NotNull
    public static MutableComponent styled(@NotNull String text, @NotNull UnaryOperator<Style> styler) {
        return Component.literal(text).withStyle(styler);
    }

    @NotNull
    public static MutableComponent colored(@NotNull String text, int color) {
        return Component.literal(text).withStyle(s -> s.withColor(color));
    }

    @NotNull
    public static MutableComponent append(@NotNull MutableComponent base, @NotNull Component... children) {
        for (Component c : children) base.append(c);
        return base;
    }

    private TextHelper() {}
}
