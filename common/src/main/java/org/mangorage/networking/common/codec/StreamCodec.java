package org.mangorage.networking.common.codec;


import org.mangorage.networking.common.util.SimpleByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface StreamCodec<B extends SimpleByteBuf, T> {
    void encode(B byteBuf, T object);
    T decode(B byteBuf);

    static <B extends SimpleByteBuf, T> StreamCodec<B, T> of(BiConsumer<B, T> encoder, Function<B, T> decoder) {
        return new StreamCodec<B, T>() {
            @Override
            public void encode(B byteBuf, T object) {
                encoder.accept(byteBuf, object);
            }

            @Override
            public T decode(B byteBuf) {
                return decoder.apply(byteBuf);
            }
        };
    }
}
