package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func16;
import java.util.function.Function;

abstract class Builder16 {
    public interface Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        <T17> Builder17.Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> field(StreamCodec<Buf, T17> codec, Function<R, T17> getter);
        StreamCodec<Buf, R> apply(Func16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> function);
    }

    record Impl<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2,
        Field<Buf, R, T3> fieldT3,
        Field<Buf, R, T4> fieldT4,
        Field<Buf, R, T5> fieldT5,
        Field<Buf, R, T6> fieldT6,
        Field<Buf, R, T7> fieldT7,
        Field<Buf, R, T8> fieldT8,
        Field<Buf, R, T9> fieldT9,
        Field<Buf, R, T10> fieldT10,
        Field<Buf, R, T11> fieldT11,
        Field<Buf, R, T12> fieldT12,
        Field<Buf, R, T13> fieldT13,
        Field<Buf, R, T14> fieldT14,
        Field<Buf, R, T15> fieldT15,
        Field<Buf, R, T16> fieldT16
    ) implements Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2, Field<Buf, R, T3> fieldT3, Field<Buf, R, T4> fieldT4, Field<Buf, R, T5> fieldT5, Field<Buf, R, T6> fieldT6, Field<Buf, R, T7> fieldT7, Field<Buf, R, T8> fieldT8, Field<Buf, R, T9> fieldT9, Field<Buf, R, T10> fieldT10, Field<Buf, R, T11> fieldT11, Field<Buf, R, T12> fieldT12, Field<Buf, R, T13> fieldT13, Field<Buf, R, T14> fieldT14, Field<Buf, R, T15> fieldT15, Field<Buf, R, T16> fieldT16) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
            this.fieldT3 = fieldT3;
            this.fieldT4 = fieldT4;
            this.fieldT5 = fieldT5;
            this.fieldT6 = fieldT6;
            this.fieldT7 = fieldT7;
            this.fieldT8 = fieldT8;
            this.fieldT9 = fieldT9;
            this.fieldT10 = fieldT10;
            this.fieldT11 = fieldT11;
            this.fieldT12 = fieldT12;
            this.fieldT13 = fieldT13;
            this.fieldT14 = fieldT14;
            this.fieldT15 = fieldT15;
            this.fieldT16 = fieldT16;
        }

        @Override
        public <T17> Builder17.Builder<Buf, R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> field(StreamCodec<Buf, T17> codec, Function<R, T17> getter) {
            return new Builder17.Impl<>(fieldT1, fieldT2, fieldT3, fieldT4, fieldT5, fieldT6, fieldT7, fieldT8, fieldT9, fieldT10, fieldT11, fieldT12, fieldT13, fieldT14, fieldT15, fieldT16, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> function) {
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
                            fieldT7.decode(buf),
                            fieldT8.decode(buf),
                            fieldT9.decode(buf),
                            fieldT10.decode(buf),
                            fieldT11.decode(buf),
                            fieldT12.decode(buf),
                            fieldT13.decode(buf),
                            fieldT14.decode(buf),
                            fieldT15.decode(buf),
                            fieldT16.decode(buf)
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
                    fieldT8.encode(buf, object);
                    fieldT9.encode(buf, object);
                    fieldT10.encode(buf, object);
                    fieldT11.encode(buf, object);
                    fieldT12.encode(buf, object);
                    fieldT13.encode(buf, object);
                    fieldT14.encode(buf, object);
                    fieldT15.encode(buf, object);
                    fieldT16.encode(buf, object);
                }
            };
        }
    }
}
