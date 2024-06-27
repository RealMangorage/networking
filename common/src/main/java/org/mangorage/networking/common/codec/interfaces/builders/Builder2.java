package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func2;
import java.util.function.Function;

abstract class Builder2 {
    public interface Builder<Buf, R, T1, T2> {
        <T3> Builder3.Builder<Buf, R, T1, T2, T3> field(StreamCodec<Buf, T3> codec, Function<R, T3> getter);
        StreamCodec<Buf, R> apply(Func2<R, T1, T2> function);
    }

    record Impl<Buf, R, T1, T2>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2
    ) implements Builder<Buf, R, T1, T2> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
        }

        @Override
        public <T3> Builder3.Builder<Buf, R, T1, T2, T3> field(StreamCodec<Buf, T3> codec, Function<R, T3> getter) {
            return new Builder3.Impl<>(fieldT1, fieldT2, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func2<R, T1, T2> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf),
                            fieldT2.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                    fieldT2.encode(buf, object);
                }
            };
        }
    }
}
