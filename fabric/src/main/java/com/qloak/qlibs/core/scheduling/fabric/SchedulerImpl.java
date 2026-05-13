package com.qloak.qlibs.core.scheduling.fabric;

import dev.architectury.event.events.client.ClientTickEvent;

@SuppressWarnings("unused")
public final class SchedulerImpl {
    public static void registerClientTick(Runnable runnable) {
        ClientTickEvent.CLIENT_POST.register(mc -> runnable.run());
    }
}
