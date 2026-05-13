# qCompat

Soft dependency stubs. These compile against the APIs but don't crash if the mod isn't present.

## JEI / REI / EMI

```java
JEIPluginBase.registerRecipeCategory(...);
JEIPluginBase.registerRecipeClickArea(...);
```

Stubs that delegate to the actually loaded recipe viewer. Only one will be active.

## Curios / Trinkets

```java
CuriosCompat.getSlot(player, "ring").ifPresent(stack -> {
    // do something with ring
});
```

Returns `Optional.empty()` if neither API is loaded.

## ModCompat Scan

```java
ModCompat.scan();
```

Checks for known mods at startup and logs which APIs are available. Called automatically by `QLibs.init()`.

## Adding New Compat

1. Create a stub interface in `common/` with a no-op default.
2. Create platform implementations in `fabric/` and `neoforge/` that check `Platform.isModLoaded()`.
3. Use `@ExpectPlatform` if the implementation differs between loaders.
