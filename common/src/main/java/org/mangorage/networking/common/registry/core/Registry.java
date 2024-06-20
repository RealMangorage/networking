package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.registry.BuiltInRegistries;

public interface Registry<T> {
    <G extends T> G get(ResourceKey resourceKey);
    <G extends T> Holder<G> getHolder(ResourceKey resourceKey);
    <G extends T> ResourceKey getId(G object);
    <G extends T> Holder<G> register(ResourceKey resourceKey, G object);

    <R extends Registry<T>> RegistryKey<R> getRegistryKey();

    static <T, R extends Registry<T> , G extends T> G register(RegistryKey<R> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.registry());
        return registry.register(resourceKey, object).get();
    }

    static <T, R extends Registry<T>, G extends T> Holder<G> registerForHolder(RegistryKey<R> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.registry());
        return registry.register(resourceKey, object);
    }

    static <T, R extends Registry<T> , G extends T> G register(R registry, ResourceKey resourceKey, G object) {
        return registerForHolder(registry, resourceKey, object).get();
    }

    static <T, R extends Registry<T>, G extends T> Holder<G> registerForHolder(R registry, ResourceKey resourceKey, G object) {
        return registerForHolder(registry.getRegistryKey(), resourceKey, object);
    }
}
