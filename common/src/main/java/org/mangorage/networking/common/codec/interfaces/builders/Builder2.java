package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func2;

import java.util.List;
import java.util.function.Function;

abstract class Builder2 {
    public interface Builder<Buf, R, A, B> {
        <C> Builder3.Builder<Buf, R, A, B, C> field(StreamCodec<Buf, C> codec, Function<R, C> getter);
        StreamCodec<Buf, R> apply(Func2<R, A, B> function);
    }

    record Impl<Buf, R, A, B>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB
    ) implements Builder2.Builder<Buf, R, A, B> {
        @Override
        public <C> Builder3.Builder<Buf, R, A, B, C> field(StreamCodec<Buf, C> codec, Function<R, C> getter) {
            return new Builder3.Impl<>(fieldA, fieldB, new Field<>(codec, getter));
        }

        @Override
        public StreamCodec<Buf, R> apply(Func2<R, A, B> function) {
            return new StreamCodec<Buf, R>() {
                private final List<Field<Buf, R, ?>> fields = List.of(fieldA, fieldB);

                @Override
                public R decode(Buf buf) {
                    return Helper.decode(buf, fields, function,
                            p -> function.apply(
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
