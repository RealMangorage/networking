package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.CodecException;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.registry.Registries;
import org.mangorage.networking.common.util.SimpleByteBuf;

import java.util.Map;

public interface Registry<T> {
    static final StreamCodec<SimpleByteBuf, Registry<?>> STREAM_CODEC = StreamCodec.<SimpleByteBuf, Registry<?>>builder()
            .field(RegistryKey.STREAM_CODEC, Registry::getRegistryKey)
            .apply(p -> {
                RegistryKey<?> registryKey = p.get();
                var registry = BuiltInRegistries.ROOT.get(registryKey.location());
                if (registry == null)
                    throw new CodecException("Unknown Registry %s".formatted(registryKey));
                return registry;
            })
            .build();


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
