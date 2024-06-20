package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.registry.BuiltInRegistries;

public interface Registry<T> {
    <G extends T> G get(ResourceKey resourceKey);
    <G extends T> Holder<G> getHolder(ResourceKey resourceKey);
    <G extends T> ResourceKey getId(G object);
    <G extends T> Holder<G> register(ResourceKey resourceKey, G object);

    RegistryKey<Registry<T>> getRegistryKey();

    static <T, G extends T> G register(RegistryKey<Registry<T>> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.registry());
        return registry.register(resourceKey, object).get();
    }

    static <T, G extends T> Holder<G> registerForHolder(RegistryKey<Registry<T>> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.registry());
        return registry.register(resourceKey, object);
    }
}
