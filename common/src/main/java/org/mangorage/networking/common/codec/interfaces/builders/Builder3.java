package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func3;
import java.util.function.Function;

abstract class Builder3 {
    public interface Builder<Buf, R, T1, T2, T3> {
        <T4> Builder4.Builder<Buf, R, T1, T2, T3, T4> field(StreamCodec<Buf, T4> codec, Function<R, T4> getter);
        StreamCodec<Buf, R> apply(Func3<R, T1, T2, T3> function);
    }

    record Impl<Buf, R, T1, T2, T3>(
        Field<Buf, R, T1> fieldT1,
        Field<Buf, R, T2> fieldT2,
        Field<Buf, R, T3> fieldT3
    ) implements Builder<Buf, R, T1, T2, T3> {

        public Impl(Field<Buf, R, T1> fieldT1, Field<Buf, R, T2> fieldT2, Field<Buf, R, T3> fieldT3) {
            this.fieldT1 = fieldT1;
            this.fieldT2 = fieldT2;
            this.fieldT3 = fieldT3;
        }

        @Override
        public <T4> Builder4.Builder<Buf, R, T1, T2, T3, T4> field(StreamCodec<Buf, T4> codec, Function<R, T4> getter) {
            return new Builder4.Impl<>(fieldT1, fieldT2, fieldT3, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func3<R, T1, T2, T3> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf),
                            fieldT2.decode(buf),
                            fieldT3.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                    fieldT2.encode(buf, object);
                    fieldT3.encode(buf, object);
                }
            };
        }
    }
}
