# qConfig

Config builder with categories and a central registry.

## Building a Config

```java
ModConfig cfg = ConfigBuilder.create("mymod")
    .category("general", "general settings")
    .defineBool("enabled", true)
    .defineInt("max_items", 64)
    .defineString("message", "hello")
    .build();

QConfig.register(cfg);
```

## Reading Values

```java
boolean on = cfg.getBool("enabled");
int max = cfg.getInt("max_items");
String msg = cfg.getString("message");
```

## Validation

```java
builder.define("range", 10, Integer.class,
    v -> v >= 1 && v <= 100 ? null : "must be 1..100");
```

Validator returns `null` for valid, or an error string.

## Reload Hooks

```java
builder.defineInt("refresh_rate", 20, "ticks between refreshes",
    null, newRate -> MySystem.setInterval(newRate));
```

The consumer fires whenever the config is reloaded.

## Persistence

Configs auto-load from `.minecraft/config/<modid>.json` when registered.

```java
// manual save
QConfig.save("mymod");

// save everything
QConfig.saveAll();
```

JSON format:
```json
{
  "general": {
    "enabled": true,
    "max_items": 64
  }
}
```

If the file doesn't exist, defaults are used. Edit the JSON and configs reload on next register (typically next launch).
