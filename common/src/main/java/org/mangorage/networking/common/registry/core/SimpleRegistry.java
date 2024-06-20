package org.mangorage.networking.common.registry.core;

import java.util.HashMap;
import java.util.Map;

public class SimpleRegistry<T> implements Registry<T> {
    public static <T> SimpleRegistry<T> of(RegistryKey<Registry<T>> key) {
        return new SimpleRegistry<T>(key);
    }

    private final RegistryKey<Registry<T>> registryKey;

    private final Map<ResourceKey, Holder<? extends T>> registered = new HashMap<>();
    private final Map<T, ResourceKey> registered_reverse = new HashMap<>();

    private SimpleRegistry(RegistryKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <G extends T> G get(ResourceKey resourceKey) {
        return (G) getHolder(resourceKey).get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <G extends T> Holder<G> getHolder(ResourceKey resourceKey) {
        return (Holder<G>) registered.get(resourceKey);
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
    public RegistryKey<Registry<T>> getRegistryKey() {
        return registryKey;
    }
}
