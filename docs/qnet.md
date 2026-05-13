# qNet

Simplified packet networking using Architectury's `CustomPacketPayload`.

## Registering a Channel

```java
public static final CustomPacketPayload.Type<MyPacket> TYPE =
    new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("mymod", "my_packet"));

public static final StreamCodec<RegistryFriendlyByteBuf, MyPacket> CODEC =
    StreamCodec.of((buf, pkt) -> buf.writeInt(pkt.value()), buf -> new MyPacket(buf.readInt()));

public static void init() {
    QNet.registerChannel(TYPE, CODEC, (packet, ctx) -> {
        // runs on the correct thread via ctx.queue()
        Player player = ctx.getPlayer();
        // handle packet...
    });
}
```

## Sending

```java
// client -> server
channel.sendToServer(new MyPacket(42));

// server -> single player
channel.sendToPlayer(serverPlayer, new MyPacket(42));

// server -> everyone in level
channel.sendToAllPlayers(level, new MyPacket(42));
```

## Notes

- `QNet` registers both C2S and S2C receivers for every channel. Use `PacketChannel` methods to control direction.
- `CustomPacketPayload` + `StreamCodec` is the 1.21 way. No more raw byte buffers.
