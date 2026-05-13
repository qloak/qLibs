package com.qloak.qlibs.waybound;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class WayboundItems {
    public static final Item WAYBOUND_COMPASS = register("waybound_compass", new WayboundCompass());

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(WayboundMod.MOD_ID, name), item);
    }

    public static void init() {
        WayboundMod.LOGGER.info("items registered");
    }

    public static final class WayboundCompass extends Item {
        public WayboundCompass() {
            super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
        }

        @Override
        public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
            ItemStack stack = player.getItemInHand(hand);
            if (level.isClientSide) {
                if (player.isShiftKeyDown()) WayboundNetworking.sendTeleportRequest();
                return InteractionResultHolder.success(stack);
            }

            if (player.isShiftKeyDown()) return InteractionResultHolder.success(stack);

            bind(stack, player);
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("bound!"), true);
            level.playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL,
                    SoundSource.PLAYERS, 0.6f, 1.3f);
            return InteractionResultHolder.success(stack);
        }

        @Override public boolean isFoil(@NotNull ItemStack stack) { return isBound(stack); }

        private static void bind(@NotNull ItemStack stack, @NotNull Player player) {
            CompoundTag tag = new CompoundTag();
            Vec3 pos = player.position();
            tag.putString("dim", player.level().dimension().location().toString());
            tag.putDouble("x", pos.x);
            tag.putDouble("y", pos.y);
            tag.putDouble("z", pos.z);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        public static boolean isBound(@NotNull ItemStack stack) {
            CustomData d = stack.get(DataComponents.CUSTOM_DATA);
            return d != null && d.copyTag().contains("dim");
        }

        public static String getDimension(@NotNull ItemStack stack) {
            CustomData d = stack.get(DataComponents.CUSTOM_DATA);
            return d != null ? d.copyTag().getString("dim") : "";
        }

        public static double getX(@NotNull ItemStack stack) {
            CustomData d = stack.get(DataComponents.CUSTOM_DATA);
            return d != null ? d.copyTag().getDouble("x") : 0;
        }

        public static double getY(@NotNull ItemStack stack) {
            CustomData d = stack.get(DataComponents.CUSTOM_DATA);
            return d != null ? d.copyTag().getDouble("y") : 0;
        }

        public static double getZ(@NotNull ItemStack stack) {
            CustomData d = stack.get(DataComponents.CUSTOM_DATA);
            return d != null ? d.copyTag().getDouble("z") : 0;
        }
    }
}
