package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.registry.BuiltInRegistries;

import java.util.Map;

public interface Registry<T> {


    <G extends T> G get(ResourceKey resourceKey);
    <G extends T> Holder<G> getHolder(ResourceKey resourceKey);
    <G extends T> ResourceKey getId(G object);
    <G extends T> Holder<G> register(ResourceKey resourceKey, G object);

    RegistryKey<? extends Registry<T>> getRegistryKey();
    void freeze();
    Map<ResourceKey, Holder<? extends T>> getEntries();

    /**
     * Helpers for registration purposes
     */

    static void registerRegistry(ResourceKey resourceKey, Registry<?> registry) {
        BuiltInRegistries.ROOT.register(resourceKey, registry);
    }

    static <T, G extends T> G register(RegistryKey<? extends Registry<T>> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.location());
        return registry.register(resourceKey, object).get();
    }

    static <T, G extends T> Holder<G> registerForHolder(RegistryKey<? extends Registry<T>> registryKey, ResourceKey resourceKey, G object) {
        Registry<T> registry = BuiltInRegistries.ROOT.get(registryKey.location());
        return registry.register(resourceKey, object);
    }

    static <T, G extends T> G register(Registry<T> registry, ResourceKey resourceKey, G object) {
        return registerForHolder(registry, resourceKey, object).get();
    }

    static <T, G extends T> Holder<G> registerForHolder(Registry<T> registry, ResourceKey resourceKey, G object) {
        return registerForHolder(registry.getRegistryKey(), resourceKey, object);
    }
}
