package com.qloak.qlibs.config;

import com.qloak.qlibs.config.validation.Validator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ConfigValue<T> implements Supplier<T> {
    private final String key;
    private final String comment;
    private final T defaultValue;
    private final Class<T> type;
    private final Validator<T> validator;
    private final Consumer<T> reloadHook;

    private T current;

    ConfigValue(@NotNull String key, @NotNull String comment, T defaultValue, @NotNull Class<T> type,
                 @Nullable Validator<T> validator, @Nullable Consumer<T> reloadHook) {
        this.key = key;
        this.comment = comment;
        this.defaultValue = defaultValue;
        this.type = type;
        this.validator = validator;
        this.reloadHook = reloadHook;
        this.current = defaultValue;
    }

    @Override
    public T get() {
        return current;
    }

    public @NotNull String key() { return key; }
    public @NotNull String comment() { return comment; }
    public T defaultValue() { return defaultValue; }
    public @NotNull Class<T> type() { return type; }

    public void set(T value) {
        if (validator != null && !validator.test(value)) {
            throw new IllegalArgumentException("Validation failed for config key '" + key + "': " + value);
        }
        this.current = value;
        if (reloadHook != null) reloadHook.accept(value);
    }

    public void reset() {
        set(defaultValue);
    }
}
