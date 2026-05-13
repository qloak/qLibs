package com.qloak.qlibs.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.NotNull;

/**
 * Fluent builder for entity attribute maps.
 */
public final class AttributeBuilder {
    private final AttributeSupplier.Builder builder = AttributeSupplier.builder();

    public static AttributeBuilder create() {
        return new AttributeBuilder();
    }

    public AttributeBuilder add(@NotNull Holder<Attribute> attribute, double base) {
        builder.add(attribute, base);
        return this;
    }

    public AttributeSupplier build() {
        return builder.build();
    }
}
