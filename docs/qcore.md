# qCore

Platform detection, scheduling, and logging.

## Platform

```java
Platform.isModLoaded("jei");
Platform.getLoaderName();      // "fabric" or "neoforge"
Platform.isDevelopmentEnvironment();
Platform.id("my_texture");     // ResourceLocation("qlibs", "my_texture")
```

## Scheduler

Tick-scheduled tasks. Works on both client and server.

```java
// one-shot after 2 seconds
long id = Scheduler.schedule(2000, () -> {
    System.out.println("fired");
});

// repeating every second, initial delay 500ms
long id2 = Scheduler.scheduleRepeating(500, 1000, () -> {
    System.out.println("tick");
});

Scheduler.cancel(id);
```

Tasks are queued onto the correct thread automatically. Don't do world mutations from client tasks.

## QLog

Thin SLF4J wrapper so you don't type `LoggerFactory` everywhere.

```java
public static final QLog LOGGER = QLog.create("mymod");

LOGGER.info("loaded on {}", Platform.getLoaderName());
LOGGER.error("something broke", exception);
```
