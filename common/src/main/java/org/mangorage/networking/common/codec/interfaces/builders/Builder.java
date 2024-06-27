package org.mangorage.networking.common.codec.interfaces.builders;



import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;

import java.util.function.Function;

public abstract class Builder<Buf, R> {
    public <A> Builder1.Builder<Buf, R, A> field(StreamCodec<Buf, A> codec, Function<R, A> getter) {
        return new Builder1.Impl<>(new Field<>(codec, getter));
    }
}
