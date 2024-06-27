package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func4;

abstract class Builder4 {
    public interface Builder<Buf, R, A, B, C, D> {
        StreamCodec<Buf, R> apply(Func4<R, A, B, C, D> function);
    }

    record Impl<Buf, R, A, B, C, D>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB,
            Field<Buf, R, C> fieldC,
            Field<Buf, R, D> fieldD
    ) implements Builder4.Builder<Buf, R, A, B, C, D> {

        @Override
        public StreamCodec<Buf, R> apply(Func4<R, A, B, C, D> function) {
            return new StreamCodec<>() {

                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf),
                            fieldB.decode(buf),
                            fieldC.decode(buf),
                            fieldD.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                    fieldB.encode(buf, object);
                    fieldC.encode(buf, object);
                    fieldD.encode(buf, object);
                }
            };
        }
    }
}