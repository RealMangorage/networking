package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.DeferredStream;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.StreamCodecConsumer;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.util.SimpleByteBuf;

import java.util.function.Consumer;

public interface Holder<T> {
    StreamCodec<SimpleByteBuf, Holder<?>> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(SimpleByteBuf byteBuf, Holder<?> object) {
            RegistryKey.STREAM_CODEC.encode(byteBuf, object.getRegistryKey());
            ResourceKey.STREAM_CODEC.encode(byteBuf, object.getId());
        }

        @Override
        public Holder<?> decode(SimpleByteBuf byteBuf) {
            var registryId = RegistryKey.STREAM_CODEC.decode(byteBuf);
            var objectId = ResourceKey.STREAM_CODEC.decode(byteBuf);

            Registry<?> registry = BuiltInRegistries.ROOT.get(registryId.registry());
            return registry.getHolder(objectId);
        }
    };

    @SuppressWarnings("unchecked")
    default <T2> Holder<T2> cast() {
        return (Holder<T2>) this;
    }

    @SuppressWarnings("unchecked")
    default <T2> T2 getCast() {
        return (T2) get();
    }

    T get();

    RegistryKey<? extends Registry<?>> getRegistryKey();
    ResourceKey getId();

    StreamCodecConsumer<SimpleByteBuf, Holder<?>, StreamCodec<SimpleByteBuf, Holder<?>>> streamCodec();
}
