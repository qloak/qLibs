package com.qloak.qlibs.net;

import com.qloak.qlibs.QLibs;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class QNet {
    private static final Map<CustomPacketPayload.Type<?>, PacketChannel<?>> CHANNELS = new HashMap<>();

    public static <T extends CustomPacketPayload> PacketChannel<T> registerChannel(
            @NotNull CustomPacketPayload.Type<T> type,
            @NotNull StreamCodec<RegistryFriendlyByteBuf, T> codec,
            @NotNull BiConsumer<T, NetworkManager.PacketContext> handler) {
        PacketChannel<T> ch = new PacketChannel<>(type, handler);
        CHANNELS.put(type, ch);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, type, codec, (pkt, ctx) -> {
            ctx.queue(() -> handler.accept(pkt, ctx));
        });
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, type, codec, (pkt, ctx) -> {
            ctx.queue(() -> handler.accept(pkt, ctx));
        });
        QLibs.LOGGER.debug("registered channel {}", type.id());
        return ch;
    }

    @SuppressWarnings("unchecked")
    public static <T extends CustomPacketPayload> PacketChannel<T> get(@NotNull CustomPacketPayload.Type<T> type) {
        return (PacketChannel<T>) CHANNELS.get(type);
    }

    private QNet() {}
}
