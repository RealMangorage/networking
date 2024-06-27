package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func7;
import java.util.function.Function;

abstract class Builder7 {
    public interface Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7> {
        <T8> Builder8.Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8> field(StreamCodec<Buf, T8> codec, Function<R, T8> getter);
        StreamCodec<Buf, R> apply(Func7<R, T1, T2, T3, T4, T5, T6, T7> function);
    }

    record Impl<Buf, R, T1, T2, T3, T4, T5, T6, T7>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2,
        Field<Buf, R, T3> fieldT3,
        Field<Buf, R, T4> fieldT4,
        Field<Buf, R, T5> fieldT5,
        Field<Buf, R, T6> fieldT6,
        Field<Buf, R, T7> fieldT7
    ) implements Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2, Field<Buf, R, T3> fieldT3, Field<Buf, R, T4> fieldT4, Field<Buf, R, T5> fieldT5, Field<Buf, R, T6> fieldT6, Field<Buf, R, T7> fieldT7) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
            this.fieldT3 = fieldT3;
            this.fieldT4 = fieldT4;
            this.fieldT5 = fieldT5;
            this.fieldT6 = fieldT6;
            this.fieldT7 = fieldT7;
        }

        @Override
        public <T8> Builder8.Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8> field(StreamCodec<Buf, T8> codec, Function<R, T8> getter) {
            return new Builder8.Impl<>(fieldT1, fieldT2, fieldT3, fieldT4, fieldT5, fieldT6, fieldT7, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func7<R, T1, T2, T3, T4, T5, T6, T7> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf),
                            fieldT2.decode(buf),
                            fieldT3.decode(buf),
                            fieldT4.decode(buf),
                            fieldT5.decode(buf),
                            fieldT6.decode(buf),
                            fieldT7.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                    fieldT2.encode(buf, object);
                    fieldT3.encode(buf, object);
                    fieldT4.encode(buf, object);
                    fieldT5.encode(buf, object);
                    fieldT6.encode(buf, object);
                    fieldT7.encode(buf, object);
                }
            };
        }
    }
}
