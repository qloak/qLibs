package com.qloak.qlibs.waybound;

import com.qloak.qlibs.net.QNet;
import com.qloak.qlibs.net.PacketChannel;
import com.qloak.qlibs.render.particle.ParticleHelper;
import com.qloak.qlibs.world.DimensionTeleport;
import com.qloak.qlibs.data.storage.PlayerDataStore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class WayboundNetworking {
    public static final CustomPacketPayload.Type<TeleportPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(WayboundMod.MOD_ID, "teleport")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TeleportPacket> CODEC =
            StreamCodec.unit(new TeleportPacket());

    private static PacketChannel<TeleportPacket> CH;

    public static void init() {
        CH = QNet.registerChannel(TYPE, CODEC, (pkt, ctx) -> {
            Player player = ctx.getPlayer();
            if (!(player instanceof ServerPlayer sp)) return;

            ItemStack held = sp.getMainHandItem();
            if (!held.is(WayboundItems.WAYBOUND_COMPASS)) {
                held = sp.getOffhandItem();
                if (!held.is(WayboundItems.WAYBOUND_COMPASS)) return;
            }

            if (!WayboundItems.WayboundCompass.isBound(held)) {
                sp.displayClientMessage(net.minecraft.network.chat.Component.literal("not bound"), true);
                return;
            }

            String dimStr = WayboundItems.WayboundCompass.getDimension(held);
            double tx = WayboundItems.WayboundCompass.getX(held);
            double ty = WayboundItems.WayboundCompass.getY(held);
            double tz = WayboundItems.WayboundCompass.getZ(held);

            ResourceLocation dimRl = ResourceLocation.tryParse(dimStr);
            if (dimRl == null) return;

            MinecraftServer srv = sp.getServer();
            if (srv == null) return;

            ResourceKey<Level> dimKey = ResourceKey.create(Registries.DIMENSION, dimRl);
            ServerLevel target = srv.getLevel(dimKey);
            if (target == null) return;

            boolean same = sp.level().dimension().equals(dimKey);
            if (!same && !WayboundConfig.crossDim.get()) {
                sp.displayClientMessage(net.minecraft.network.chat.Component.literal("cross-dim disabled"), true);
                return;
            }

            long now = System.currentTimeMillis();
            long cd = WayboundConfig.cdSec.get() * 1000L;
            long last = PlayerDataStore.getLong(sp, "waybound:last_teleport", 0);
            if (now - last < cd) {
                int rem = (int) ((cd - (now - last)) / 1000);
                sp.displayClientMessage(net.minecraft.network.chat.Component.literal("cd: " + rem + "s"), true);
                return;
            }

            int cost = WayboundConfig.costXp.get();
            if (sp.experienceLevel < cost) {
                sp.displayClientMessage(net.minecraft.network.chat.Component.literal("need " + cost + " xp"), true);
                return;
            }

            sp.giveExperienceLevels(-cost);
            PlayerDataStore.putLong(sp, "waybound:last_teleport", now);

            ParticleHelper.spawnBurst(sp.level(), net.minecraft.core.particles.ParticleTypes.PORTAL,
                    sp.position(), 0.8, 30);

            DimensionTeleport.teleport(sp, target, tx, ty + 0.5, tz, sp.getYRot(), sp.getXRot());

            sp.level().playSound(null, sp.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 0.8f, 1.0f);
            ParticleHelper.spawnBurst(target, net.minecraft.core.particles.ParticleTypes.PORTAL,
                    new Vec3(tx, ty + 0.5, tz), 0.8, 30);

            sp.displayClientMessage(net.minecraft.network.chat.Component.literal("whoosh"), true);
        });
    }

    public static void sendTeleportRequest() {
        if (CH != null) CH.sendToServer(new TeleportPacket());
    }

    public record TeleportPacket() implements CustomPacketPayload {
        @Override
        public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
