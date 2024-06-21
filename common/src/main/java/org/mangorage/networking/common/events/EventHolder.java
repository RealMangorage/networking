package org.mangorage.networking.common.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class EventHolder<T> {
    public static <T> EventHolder<T> of() {
        return new EventHolder<>();
    }

    private final List<Consumer<T>> listeners = new ArrayList<>();

    private EventHolder() {}

    public void fire(T event) {
        listeners.forEach(e -> e.accept(event));
    }

    public void addListener(Consumer<T> listener) {
        this.listeners.add(listener);
    }
}
