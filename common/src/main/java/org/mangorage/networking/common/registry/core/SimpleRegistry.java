package org.mangorage.networking.common.registry.core;

import java.util.HashMap;
import java.util.Map;

public class SimpleRegistry<T> implements Registry<T> {
    public static <T> SimpleRegistry<T> of(RegistryKey<? extends Registry<T>> key) {
        return new SimpleRegistry<T>(key);
    }

    private final RegistryKey<? extends Registry<T>> registryKey;

    private final Map<ResourceKey, Holder<? extends T>> registered = new HashMap<>();
    private final Map<T, ResourceKey> registered_reverse = new HashMap<>();
    private boolean frozen = false;

    private SimpleRegistry(RegistryKey<? extends Registry<T>> registryKey) {
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
        if (frozen)
            throw new IllegalStateException("Attempted to register Object with ID %s for Registry %s which is frozen".formatted(resourceKey, getRegistryKey()));
        //if (registered.containsKey(resourceKey))
            //throw new IllegalStateException("Attempted to register Object with duplicate ID %s for Registry %s".formatted(resourceKey, getRegistryKey()));
        Holder<G> holder = SimpleHolder.of(object, registryKey, resourceKey);
        registered.put(resourceKey, holder);
        registered_reverse.put(object, resourceKey);
        return holder;
    }

    @Override
    public RegistryKey<? extends Registry<T>> getRegistryKey() {
        return registryKey;
    }

    @Override
    public void freeze() {
        this.frozen = true;
    }

    @Override
    public Map<ResourceKey, Holder<? extends T>> getEntries() {
        return Map.copyOf(registered);
    }
}
