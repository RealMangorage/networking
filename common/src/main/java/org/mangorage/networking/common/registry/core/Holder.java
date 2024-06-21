package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.CodecException;
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

            Registry<?> registry = BuiltInRegistries.ROOT.get(registryId.location());
            if (registry == null)
                throw new CodecException("Unkown Registry %s".formatted(registryId));
            Holder<?> holder = registry.getHolder(objectId);
            if (holder == null)
                throw new CodecException("Unknown Key %s for Registry %s".formatted(objectId, registryId));
            return holder;
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

    <B extends SimpleByteBuf> StreamCodec<B, Holder<?>> streamCodec();

    default <B extends SimpleByteBuf> void encode(B buf) {
        streamCodec().encode(buf, this);
    }
}
