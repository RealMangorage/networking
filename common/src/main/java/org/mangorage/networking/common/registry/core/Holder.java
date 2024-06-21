package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.CodecException;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.util.SimpleByteBuf;

public interface Holder<T> {
    StreamCodec<SimpleByteBuf, Holder<?>> STREAM_CODEC = StreamCodec.<SimpleByteBuf, Holder<?>>builder()
            .field(Registry.STREAM_CODEC, h -> {
                Registry<?> registry = BuiltInRegistries.ROOT.get(h.getRegistryKey().location());
                if (registry == null)
                    throw new CodecException("Unknown Registry %s".formatted(h.getRegistryKey()));
                return registry;
            })
            .field(ResourceKey.STREAM_CODEC, Holder::getId)
            .apply(p -> {
                Registry<?> registry = p.get();
                ResourceKey key = p.get();
                Holder<?> holder = registry.getHolder(key);
                if (holder == null)
                    throw new CodecException("Unknown Key %s for Registry %s".formatted(key, registry.getRegistryKey()));
                return holder;
            })
            .build();

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
