# qRender

HUD overlays, widgets, and particle helpers.

## HUD Overlay

Register a HUD renderer during mod init:

```java
HudOverlay.init();   // call once
HudOverlay.register((graphics, delta) -> {
    graphics.drawString(Minecraft.getInstance().font,
        Component.literal("Hello HUD"), 4, 4, 0xFFFFFF);
});
```

## Widgets

Widgets are simple. Build a tree, render it.

```java
QPanel panel = new QPanel(0, 0, 120, 50).background(0xCC000000);
panel.addChild(new QLabel(5, 5, Component.literal("Title")).color(0xFFAA55));
panel.addChild(new QProgressBar(5, 20, 110, 8).progress(0.75f));

// position panel on screen
HudAnchor a = HudAnchor.topRight(8, 8);
panel.setPosition(a.resolveX(panel.width()), a.resolveY(panel.height()));
panel.render(graphics, mouseX, mouseY, 0);
```

| Widget | What it does |
|--------|-------------|
| `QLabel` | Text with color and shadow |
| `QProgressBar` | Horizontal bar, 0..1 |
| `QButton` | Clickable with hover state |
| `QPanel` | Container with background, offsets children |

`QPanel` uses pose translation so children are positioned relative to the panel origin. Don't set children to screen coordinates.

## Anchors

```java
HudAnchor.topLeft(8, 8);
HudAnchor.topRight(8, 8);
HudAnchor.bottomLeft(8, 8);
HudAnchor.bottomRight(8, 8);
HudAnchor.topCenter(0, 8);
```

## Particles

```java
// client-side burst
ParticleHelper.spawnBurst(level, ParticleTypes.PORTAL, centerVec3, spread, count);

// server-side (sent to all players)
ParticleHelper.spawn(level, ParticleTypes.FLAME, x, y, z, vx, vy, vz, count);
```

`spawnBurst` is client-only. `spawn` auto-detects side.
