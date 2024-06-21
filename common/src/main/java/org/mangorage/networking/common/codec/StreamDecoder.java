package org.mangorage.networking.common.codec;

public interface StreamDecoder<B, O> {
    O decode(B buf);
}
