package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;

public record RegistryKey<T>(ResourceKey registry, ResourceKey location) {
    public static final StreamCodec<SimpleByteBuf, RegistryKey<?>> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(SimpleByteBuf byteBuf, RegistryKey<?> object) {
            ResourceKey.STREAM_CODEC.encode(byteBuf, object.registry());
            ResourceKey.STREAM_CODEC.encode(byteBuf, object.location());
        }

        @Override
        public RegistryKey<?> decode(SimpleByteBuf byteBuf) {
            return new RegistryKey<>(ResourceKey.STREAM_CODEC.decode(byteBuf), ResourceKey.STREAM_CODEC.decode(byteBuf));
        }
    };

    public String toString() {
        return "RegistryKey[%s:%s / %s:%s]".formatted(
                registry.namespace(),
                registry.path(),
                location.namespace(),
                location.path()
        );
    }
}
