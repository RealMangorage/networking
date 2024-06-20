package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.DeferredStream;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.StreamCodecConsumer;
import org.mangorage.networking.common.util.SimpleByteBuf;

public class SimpleHolder<T> implements Holder<T> {

    static <T> SimpleHolder<T> of(T object, RegistryKey<? extends Registry<?>> registryKey, ResourceKey id) {
        return new SimpleHolder<>(object, registryKey, id);
    }

    private final T object;
    private final RegistryKey<? extends Registry<?>> registryKey;
    private final ResourceKey id;
    private final DeferredStream<SimpleByteBuf, Holder<?>> deferredStream = new DeferredStream<>(STREAM_CODEC, this);

    private SimpleHolder(T object, RegistryKey<? extends Registry<?>> registryKey, ResourceKey id) {
        this.object = object;
        this.registryKey = registryKey;
        this.id = id;
    }

    @Override
    public T get() {
        return object;
    }

    @Override
    public RegistryKey<? extends Registry<?>> getRegistryKey() {
        return registryKey;
    }

    @Override
    public ResourceKey getId() {
        return id;
    }

    @Override
    public StreamCodecConsumer<SimpleByteBuf, Holder<?>, StreamCodec<SimpleByteBuf, Holder<?>>> streamCodec() {
        return () -> deferredStream;
    }
}
