package com.qloak.qlibs.config;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record ConfigCategory(
        @NotNull String name,
        @NotNull String description,
        @NotNull List<ConfigValue<?>> values
) {
    public ConfigCategory(@NotNull String name) {
        this(name, "", Collections.emptyList());
    }
}
