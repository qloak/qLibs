# qData

JSON loading, dynamic registries, and player data.

## PlayerDataStore

In-memory NBT per player. Good for session state, cooldowns, temp flags.

```java
// store
PlayerDataStore.putLong(player, "mymod:last_used", System.currentTimeMillis());
PlayerDataStore.putInt(player, "mymod:charges", 3);
PlayerDataStore.putString(player, "mymod:mode", "fast");

// retrieve
long last = PlayerDataStore.getLong(player, "mymod:last_used", 0);
int charges = PlayerDataStore.getInt(player, "mymod:charges", 0);
String mode = PlayerDataStore.getString(player, "mymod:mode", "normal");
```

Data lives in a `ConcurrentHashMap<UUID, CompoundTag>`. It's cleared on disconnect / server stop. For persistent data, use `SavedData`.

## JsonLoader

```java
// load all JSON files under data/<namespace>/mymod_data/
List<JsonElement> entries = JsonLoader.load("mymod_data", "mymod");
```

Merges datapack overrides. Last-loaded wins.

## DynamicRegistry

```java
DynamicRegistry<MyObject> reg = new DynamicRegistry<>();
reg.register(id("my_object"), new MyObject());
MyObject obj = reg.get(id("my_object"));
```

Simple wrapper around a map with freeze support.

## Resource Conditions

```java
ResourceConditions.register("mymod:mod_loaded", json ->
    Platform.isModLoaded(json.getAsJsonPrimitive("modid").getAsString()));
```

Use in recipes or loot tables to gate content behind soft dependencies.
