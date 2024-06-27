package org.mangorage.networking.common.codec.interfaces.builders;



import org.mangorage.networking.common.codec.StreamCodec;

import java.util.function.Function;

public abstract class Builder<Buf, R> {
    public <A> Builder1.Builder<Buf, R, A> field(StreamCodec<Buf, A> codecA, Function<R, A> functionA) {
        return new Builder1.Impl<>(codecA, functionA);
    }
}
