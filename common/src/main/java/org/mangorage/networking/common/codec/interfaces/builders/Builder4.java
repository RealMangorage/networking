package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func4;
import java.util.function.Function;

abstract class Builder4 {
    public interface Builder<Buf, R, T1, T2, T3, T4> {
        <T5> Builder5.Builder<Buf, R, T1, T2, T3, T4, T5> field(StreamCodec<Buf, T5> codec, Function<R, T5> getter);
        StreamCodec<Buf, R> apply(Func4<R, T1, T2, T3, T4> function);
    }

    record Impl<Buf, R, T1, T2, T3, T4>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2,
        Field<Buf, R, T3> fieldT3,
        Field<Buf, R, T4> fieldT4
    ) implements Builder<Buf, R, T1, T2, T3, T4> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2, Field<Buf, R, T3> fieldT3, Field<Buf, R, T4> fieldT4) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
            this.fieldT3 = fieldT3;
            this.fieldT4 = fieldT4;
        }

        @Override
        public <T5> Builder5.Builder<Buf, R, T1, T2, T3, T4, T5> field(StreamCodec<Buf, T5> codec, Function<R, T5> getter) {
            return new Builder5.Impl<>(fieldT1, fieldT2, fieldT3, fieldT4, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func4<R, T1, T2, T3, T4> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf),
                            fieldT2.decode(buf),
                            fieldT3.decode(buf),
                            fieldT4.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                    fieldT2.encode(buf, object);
                    fieldT3.encode(buf, object);
                    fieldT4.encode(buf, object);
                }
            };
        }
    }
}
