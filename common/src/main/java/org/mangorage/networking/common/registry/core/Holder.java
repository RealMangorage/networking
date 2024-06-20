package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.util.SimpleByteBuf;

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

    T get();

    RegistryKey<? extends Registry<?>> getRegistryKey();
    ResourceKey getId();

    StreamCodec<SimpleByteBuf, Holder<?>> streamCodec();
}
