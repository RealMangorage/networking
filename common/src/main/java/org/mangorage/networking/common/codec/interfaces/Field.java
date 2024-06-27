package org.mangorage.networking.common.codec.interfaces;


import org.mangorage.networking.common.codec.StreamCodec;

import java.util.function.Function;

record Field<B, M, T>(StreamCodec<B, T> fieldCodec, Function<M, T> mainToField) {
    public T decode(B buf) {
        return fieldCodec.decode(buf);
    }

    public void encode(B buf, M object) {
        fieldCodec.encode(buf, mainToField.apply(object));
    }
}