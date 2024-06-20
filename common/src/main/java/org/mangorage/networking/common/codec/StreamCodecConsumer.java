package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

import java.util.function.Consumer;

@FunctionalInterface
public interface StreamCodecConsumer<T extends SimpleByteBuf, O, C extends StreamCodec<T, O>> extends StreamCodec<T, O> {
    DeferredStream<T, O> get();

    default void encode(T byteBuf) {
        encode(byteBuf, get().object());
    }

    @Override
    default void encode(T byteBuf, O object) {
        get().streamCodec().encode(byteBuf, object);
    }

    default O decode(T byteBuf) {
        return get().streamCodec().decode(byteBuf);
    }
}
