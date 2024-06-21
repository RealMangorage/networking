package org.mangorage.networking.common.codec;


public interface StreamEncoder<B, O> {
    void encode(B buf, O object);
}
