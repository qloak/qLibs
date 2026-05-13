package com.qloak.qlibs.data.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Per-player NBT storage backed by {@link SavedData} on the server.
 * On the client a non-persistent in-memory cache is used as a fallback.
 */
public final class PlayerDataStore {
    private static final String DATA_ID = "qloak_player_data";
    // client-side fallback cache (not persisted)
    private static final Map<UUID, CompoundTag> CLIENT_CACHE = new ConcurrentHashMap<>();

    private static PlayerDataSavedData getSavedData(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            return serverLevel.getServer().overworld().getDataStorage()
                    .computeIfAbsent(PlayerDataSavedData.FACTORY, DATA_ID);
        }
        return null;
    }

    @NotNull
    public static CompoundTag of(@NotNull Player player) {
        PlayerDataSavedData saved = getSavedData(player);
        if (saved != null) {
            return saved.of(player.getUUID());
        }
        return CLIENT_CACHE.computeIfAbsent(player.getUUID(), u -> new CompoundTag());
    }

    public static void remove(@NotNull Player player) {
        PlayerDataSavedData saved = getSavedData(player);
        if (saved != null) {
            saved.remove(player.getUUID());
        } else {
            CLIENT_CACHE.remove(player.getUUID());
        }
    }

    public static boolean has(@NotNull Player player, @NotNull String key) {
        CompoundTag tag = of(player);
        return tag.contains(key);
    }

    public static void putLong(@NotNull Player player, @NotNull String key, long value) {
        CompoundTag t = of(player);
        t.putLong(key, value);
        markDirty(player);
    }

    public static long getLong(@NotNull Player player, @NotNull String key, long def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_LONG) ? t.getLong(key) : def;
    }

    public static void putInt(@NotNull Player player, @NotNull String key, int value) {
        CompoundTag t = of(player);
        t.putInt(key, value);
        markDirty(player);
    }

    public static int getInt(@NotNull Player player, @NotNull String key, int def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_INT) ? t.getInt(key) : def;
    }

    public static void putString(@NotNull Player player, @NotNull String key, @NotNull String value) {
        CompoundTag t = of(player);
        t.putString(key, value);
        markDirty(player);
    }

    @NotNull
    public static String getString(@NotNull Player player, @NotNull String key, @NotNull String def) {
        CompoundTag t = of(player);
        return t.contains(key, net.minecraft.nbt.Tag.TAG_STRING) ? t.getString(key) : def;
    }

    private static void markDirty(Player player) {
        PlayerDataSavedData saved = getSavedData(player);
        if (saved != null) {
            saved.setDirty();
        }
    }

    private static final class PlayerDataSavedData extends SavedData {
        private final Map<UUID, CompoundTag> data = new ConcurrentHashMap<>();

        PlayerDataSavedData() {}

        PlayerDataSavedData(CompoundTag tag, HolderLookup.Provider provider) {
            for (String key : tag.getAllKeys()) {
                data.put(UUID.fromString(key), tag.getCompound(key));
            }
        }

        @Override
        public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
            for (Map.Entry<UUID, CompoundTag> entry : data.entrySet()) {
                tag.put(entry.getKey().toString(), entry.getValue().copy());
            }
            return tag;
        }

        CompoundTag of(UUID uuid) {
            return data.computeIfAbsent(uuid, u -> new CompoundTag());
        }

        void remove(UUID uuid) {
            data.remove(uuid);
            setDirty();
        }

        static final SavedData.Factory<PlayerDataSavedData> FACTORY = new SavedData.Factory<>(
                PlayerDataSavedData::new,
                (tag, provider) -> new PlayerDataSavedData(tag, provider),
                DataFixTypes.LEVEL
        );
    }

    private PlayerDataStore() {}
}
