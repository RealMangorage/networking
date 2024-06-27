package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func5;
import java.util.function.Function;

abstract class Builder5 {
    public interface Builder<Buf, R, T1, T2, T3, T4, T5> {
        <T6> Builder6.Builder<Buf, R, T1, T2, T3, T4, T5, T6> field(StreamCodec<Buf, T6> codec, Function<R, T6> getter);
        StreamCodec<Buf, R> apply(Func5<R, T1, T2, T3, T4, T5> function);
    }

    record Impl<Buf, R, T1, T2, T3, T4, T5>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2,
        Field<Buf, R, T3> fieldT3,
        Field<Buf, R, T4> fieldT4,
        Field<Buf, R, T5> fieldT5
    ) implements Builder<Buf, R, T1, T2, T3, T4, T5> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2, Field<Buf, R, T3> fieldT3, Field<Buf, R, T4> fieldT4, Field<Buf, R, T5> fieldT5) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
            this.fieldT3 = fieldT3;
            this.fieldT4 = fieldT4;
            this.fieldT5 = fieldT5;
        }

        @Override
        public <T6> Builder6.Builder<Buf, R, T1, T2, T3, T4, T5, T6> field(StreamCodec<Buf, T6> codec, Function<R, T6> getter) {
            return new Builder6.Impl<>(fieldT1, fieldT2, fieldT3, fieldT4, fieldT5, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func5<R, T1, T2, T3, T4, T5> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf),
                            fieldT2.decode(buf),
                            fieldT3.decode(buf),
                            fieldT4.decode(buf),
                            fieldT5.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                    fieldT2.encode(buf, object);
                    fieldT3.encode(buf, object);
                    fieldT4.encode(buf, object);
                    fieldT5.encode(buf, object);
                }
            };
        }
    }
}
