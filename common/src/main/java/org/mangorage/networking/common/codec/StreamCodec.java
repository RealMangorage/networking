package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

public interface StreamCodec<B extends SimpleByteBuf, T> extends StreamEncoder<B, T>, StreamDecoder<B, T> {

    static <B extends SimpleByteBuf, T> StreamCodec<B, T> of(StreamEncoder<B, T> encoder, StreamDecoder<B, T> decoder) {
        return new StreamCodec<B, T>() {
            @Override
            public void encode(B byteBuf, T object) {
                encoder.encode(byteBuf, object);
            }

            @Override
            public T decode(B byteBuf) {
                return decoder.decode(byteBuf);
            }
        };
    }
}
