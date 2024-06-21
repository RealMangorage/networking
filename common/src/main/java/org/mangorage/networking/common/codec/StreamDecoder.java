package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

public interface StreamDecoder<B extends SimpleByteBuf, O> {
    O decode(B buf);
}
