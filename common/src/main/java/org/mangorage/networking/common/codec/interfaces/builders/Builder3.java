package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.Helper;
import org.mangorage.networking.common.codec.interfaces.functions.Func3;

import java.util.List;
import java.util.function.Function;

abstract class Builder3 {
    public interface Builder<Buf, R, A, B, C> {
        <D> Builder4.Builder<Buf, R, A, B, C, D> field(StreamCodec<Buf, D> codec, Function<R, D> getter);
        StreamCodec<Buf, R> apply(Func3<R, A, B, C> function);
    }

    record Impl<Buf, R, A, B, C>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB,
            Field<Buf, R, C> fieldC
    ) implements Builder3.Builder<Buf, R, A, B, C> {

        @Override
        public <D> Builder4.Builder<Buf, R, A, B, C, D> field(StreamCodec<Buf, D> codec, Function<R, D> getter) {
            return new Builder4.Impl<>(fieldA, fieldB, fieldC, new Field<>(codec, getter));
        }

        @Override
        public StreamCodec<Buf, R> apply(Func3<R, A, B, C> function) {
            return new StreamCodec<Buf, R>() {
                private final List<Field<Buf, R, ?>> fields = List.of(fieldA, fieldB, fieldC);

                @Override
                public R decode(Buf buf) {
                    return Helper.decode(buf, fields, function,
                            p -> function.apply(
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
