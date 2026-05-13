package com.qloak.qlibs.utils.cache;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Thread-safe lazy initializer.
 */
public final class Lazy<T> implements Supplier<T> {
    private final Supplier<T> factory;
    private volatile T value;
    private volatile boolean initialized = false;

    private Lazy(@NotNull Supplier<T> factory) {
        this.factory = factory;
    }

    public static <T> Lazy<T> of(@NotNull Supplier<T> factory) {
        return new Lazy<>(factory);
    }

    @Override
    public T get() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    value = factory.get();
                    initialized = true;
                }
            }
        }
        return value;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void invalidate() {
        synchronized (this) {
            initialized = false;
            value = null;
        }
    }
}
