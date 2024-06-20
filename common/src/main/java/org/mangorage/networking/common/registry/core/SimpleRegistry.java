package org.mangorage.networking.common.registry.core;

import java.util.HashMap;
import java.util.Map;

public class SimpleRegistry<T, R extends Registry<T>> implements Registry<T> {
    public static <T, R extends Registry<T>> SimpleRegistry<T, R> of(RegistryKey<R> key) {
        return new SimpleRegistry<T, R>(key);
    }

    private final RegistryKey<R> registryKey;

    private final Map<ResourceKey, Holder<? extends T>> registered = new HashMap<>();
    private final Map<T, ResourceKey> registered_reverse = new HashMap<>();

    private SimpleRegistry(RegistryKey<R> registryKey) {
        this.registryKey = registryKey;
    }


    @Override
    public <G extends T> G get(ResourceKey resourceKey) {
        return getHolder(resourceKey).getCast();
    }

    @Override
    public <G extends T> Holder<G> getHolder(ResourceKey resourceKey) {
        return registered.get(resourceKey).cast();
    }

    @Override
    public <G extends T> ResourceKey getId(G object) {
        return registered_reverse.get(object);
    }

    @Override
    public <G extends T> Holder<G> register(ResourceKey resourceKey, G object) {
        Holder<G> holder = SimpleHolder.of(object, registryKey, resourceKey);
        registered.put(resourceKey, holder);
        registered_reverse.put(object, resourceKey);
        return holder;
    }

    @Override
    public <R2 extends Registry<T>> RegistryKey<R2> getRegistryKey() {
        return registryKey.cast();
    }
}
