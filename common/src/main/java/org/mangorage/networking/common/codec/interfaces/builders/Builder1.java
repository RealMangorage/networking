package org.mangorage.networking.common.codec.interfaces.builders;


import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func1;

import java.util.function.Function;

abstract class Builder1 {
    public interface Builder<Buf, R, A> {
        <B> Builder2.Builder<Buf, R, A, B> field(StreamCodec<Buf, B> codec, Function<R, B> getter);
        StreamCodec<Buf, R> apply(Func1<R, A> function);
    }

    record Impl<Buf, R, A>(
            Field<Buf, R, A> fieldA
    ) implements Builder1.Builder<Buf, R, A> {

        @Override
        public <B> Builder2.Builder<Buf, R, A, B> field(StreamCodec<Buf, B> codec, Function<R, B> getter) {
            return new Builder2.Impl<>(fieldA, new Field<>(codec, getter));
        }

        @Override
        public StreamCodec<Buf, R> apply(Func1<R, A> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                }
            };
        }
    }
}

