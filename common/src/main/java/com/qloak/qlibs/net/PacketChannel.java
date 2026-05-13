package com.qloak.qlibs.net;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public final class PacketChannel<T extends CustomPacketPayload> {
    private final CustomPacketPayload.Type<T> type;
    private final BiConsumer<T, NetworkManager.PacketContext> handler;

    PacketChannel(@NotNull CustomPacketPayload.Type<T> type,
                  @NotNull BiConsumer<T, NetworkManager.PacketContext> handler) {
        this.type = type;
        this.handler = handler;
    }

    public void sendToServer(@NotNull T pkt) {
        NetworkManager.sendToServer(pkt);
    }

    public void sendToPlayer(@NotNull ServerPlayer player, @NotNull T pkt) {
        NetworkManager.sendToPlayer(player, pkt);
    }

    public void sendToAllPlayers(@NotNull Level level, @NotNull T pkt) {
        if (level.isClientSide()) return;
        for (Player p : level.players()) {
            if (p instanceof ServerPlayer sp) NetworkManager.sendToPlayer(sp, pkt);
        }
    }

    public @NotNull CustomPacketPayload.Type<T> type() {
        return type;
    }
}
