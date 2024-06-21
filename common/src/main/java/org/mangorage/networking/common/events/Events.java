package org.mangorage.networking.common.events;

import org.mangorage.networking.common.events.registry.RegisterEvent;

public class Events {
    public static final EventHolder<RegisterEvent> REGISTRY_EVENT = EventHolder.of();
    public static final EventHolder<Void> FINISH_LOADING_EVENT = EventHolder.of();
}
