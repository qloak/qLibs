package com.qloak.qlibs.core.scheduling;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import org.jetbrains.annotations.NotNull;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

public final class Scheduler {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private static final PriorityQueue<Task> TASKS = new PriorityQueue<>();
    private static boolean init = false;

    public static void init() {
        if (init) return;
        init = true;
        TickEvent.SERVER_POST.register(s -> runPending());
        registerClientTick(() -> runPending());
    }

    @ExpectPlatform
    public static void registerClientTick(Runnable r) {
        throw new AssertionError();
    }

    private static void runPending() {
        long now = System.currentTimeMillis();
        synchronized (TASKS) {
            while (!TASKS.isEmpty() && TASKS.peek().at <= now) {
                Task t = TASKS.poll();
                if (t == null) continue;
                try {
                    t.r.run();
                } catch (Exception e) {
                    com.qloak.qlibs.QLibs.LOGGER.error("task crashed", e);
                }
                if (t.repeat && t.period > 0) {
                    t.at = now + t.period;
                    TASKS.add(t);
                }
            }
        }
    }

    public static long schedule(long delayMs, @NotNull Runnable r) {
        long id = COUNTER.incrementAndGet();
        synchronized (TASKS) {
            TASKS.add(new Task(id, System.currentTimeMillis() + delayMs, 0, false, r));
        }
        return id;
    }

    public static long scheduleRepeating(long delayMs, long periodMs, @NotNull Runnable r) {
        long id = COUNTER.incrementAndGet();
        synchronized (TASKS) {
            TASKS.add(new Task(id, System.currentTimeMillis() + delayMs, periodMs, true, r));
        }
        return id;
    }

    public static boolean cancel(long id) {
        synchronized (TASKS) {
            return TASKS.removeIf(t -> t.id == id);
        }
    }

    private static final class Task implements Comparable<Task> {
        final long id;
        long at;
        final long period;
        final boolean repeat;
        final Runnable r;

        Task(long id, long at, long period, boolean repeat, Runnable r) {
            this.id = id; this.at = at; this.period = period; this.repeat = repeat; this.r = r;
        }

        @Override public int compareTo(@NotNull Task o) {
            return Long.compare(at, o.at);
        }
    }

    private Scheduler() {}
}
