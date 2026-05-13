package com.qloak.qlibs.data.storage;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
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
    private static final Map<UUID, CompoundTag> CLIENT_CACHE = new ConcurrentHashMap<>();

    private static PlayerDataSavedData getSavedData(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            return serverLevel.getServer().overworld().getDataStorage()
                    .computeIfAbsent(PlayerDataSavedData.TYPE);
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
        return of(player).getLongOr(key, def);
    }

    public static void putInt(@NotNull Player player, @NotNull String key, int value) {
        CompoundTag t = of(player);
        t.putInt(key, value);
        markDirty(player);
    }

    public static int getInt(@NotNull Player player, @NotNull String key, int def) {
        return of(player).getIntOr(key, def);
    }

    public static void putString(@NotNull Player player, @NotNull String key, @NotNull String value) {
        CompoundTag t = of(player);
        t.putString(key, value);
        markDirty(player);
    }

    @NotNull
    public static String getString(@NotNull Player player, @NotNull String key, @NotNull String def) {
        return of(player).getStringOr(key, def);
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

        static final Codec<PlayerDataSavedData> CODEC = CompoundTag.CODEC.xmap(
                tag -> {
                    PlayerDataSavedData saved = new PlayerDataSavedData();
                    for (String key : tag.keySet()) {
                        saved.data.put(UUID.fromString(key), tag.getCompoundOrEmpty(key));
                    }
                    return saved;
                },
                saved -> {
                    CompoundTag tag = new CompoundTag();
                    for (Map.Entry<UUID, CompoundTag> e : saved.data.entrySet()) {
                        tag.put(e.getKey().toString(), e.getValue().copy());
                    }
                    return tag;
                }
        );

        static final SavedDataType<PlayerDataSavedData> TYPE = new SavedDataType<>(
                DATA_ID,
                PlayerDataSavedData::new,
                CODEC,
                DataFixTypes.LEVEL
        );

        CompoundTag of(UUID uuid) {
            return data.computeIfAbsent(uuid, u -> new CompoundTag());
        }

        void remove(UUID uuid) {
            data.remove(uuid);
            setDirty();
        }
    }

    private PlayerDataStore() {}
}
