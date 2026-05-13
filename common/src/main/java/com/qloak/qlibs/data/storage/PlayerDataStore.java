package com.qloak.qlibs.data.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// in-memory NBT per player. not disk-persistent, use SavedData for that
public final class PlayerDataStore {
    private static final Map<UUID, CompoundTag> DATA = new ConcurrentHashMap<>();

    @NotNull
    public static CompoundTag of(@NotNull Player player) {
        return DATA.computeIfAbsent(player.getUUID(), u -> new CompoundTag());
    }

    public static void remove(@NotNull Player player) {
        DATA.remove(player.getUUID());
    }

    public static boolean has(@NotNull Player player, @NotNull String key) {
        CompoundTag tag = DATA.get(player.getUUID());
        return tag != null && tag.contains(key);
    }

    public static void putLong(@NotNull Player player, @NotNull String key, long value) {
        of(player).putLong(key, value);
    }

    public static long getLong(@NotNull Player player, @NotNull String key, long def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_LONG) ? t.getLong(key) : def;
    }

    public static void putInt(@NotNull Player player, @NotNull String key, int value) {
        of(player).putInt(key, value);
    }

    public static int getInt(@NotNull Player player, @NotNull String key, int def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_INT) ? t.getInt(key) : def;
    }

    public static void putString(@NotNull Player player, @NotNull String key, @NotNull String value) {
        of(player).putString(key, value);
    }

    @NotNull
    public static String getString(@NotNull Player player, @NotNull String key, @NotNull String def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_STRING) ? t.getString(key) : def;
    }

    public static void clear() { DATA.clear(); }

    private PlayerDataStore() {}
}
