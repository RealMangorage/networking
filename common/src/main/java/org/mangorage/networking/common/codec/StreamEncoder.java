package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

public interface StreamEncoder<B extends SimpleByteBuf, O> {
    void encode(B buf, O object);
}
