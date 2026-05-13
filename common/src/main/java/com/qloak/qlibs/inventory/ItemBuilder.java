package com.qloak.qlibs.inventory;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public final class ItemBuilder {
    private final Item.Properties props = new Item.Properties();

    public static ItemBuilder create() { return new ItemBuilder(); }

    public ItemBuilder stackSize(int max) { props.stacksTo(max); return this; }
    public ItemBuilder durability(int d) { props.durability(d); return this; }
    public ItemBuilder fireResistant() { props.fireResistant(); return this; }
    public ItemBuilder food(int nutrition, float saturation) {
        props.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build());
        return this;
    }

    public Item.Properties build() { return props; }
}
