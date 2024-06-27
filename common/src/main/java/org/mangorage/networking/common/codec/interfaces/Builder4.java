package org.mangorage.networking.common.codec.interfaces;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.functions.Func4;

import java.util.List;

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
            return new StreamCodec<Buf, R>() {
                private final List<Field<Buf, R, ?>> fields = List.of(fieldA, fieldB, fieldC, fieldD);

                @Override
                public R decode(Buf buf) {
                    return Helper.decode(buf, fields, function,
                            p -> function.apply(
                                    p.get(),
                                    p.get(),
                                    p.get(),
                                    p.get()
                            )
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    Helper.encode(buf, object, fields);
                }
            };
        }
    }
}