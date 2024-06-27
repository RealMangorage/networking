package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.Field;
import org.mangorage.networking.common.codec.interfaces.functions.Func1;
import java.util.function.Function;

abstract class Builder1 {
    public interface Builder<Buf, R, T1> {
        <T2> Builder2.Builder<Buf, R, T1, T2> field(StreamCodec<Buf, T2> codec, Function<R, T2> getter);
        StreamCodec<Buf, R> apply(Func1<R, T1> function);
    }

    record Impl<Buf, R, T1>(
        Field<Buf, R, T1> fieldT1
    ) implements Builder<Buf, R, T1> {

        public Impl(Field<Buf, R, T1> fieldT1) {
            this.fieldT1 = fieldT1;
        }

        @Override
        public <T2> Builder2.Builder<Buf, R, T1, T2> field(StreamCodec<Buf, T2> codec, Function<R, T2> getter) {
            return new Builder2.Impl<>(fieldT1, new Field<>(codec, getter)
            );
        }

        @Override
        public StreamCodec<Buf, R> apply(Func1<R, T1> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldT1.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldT1.encode(buf, object);
                }
            };
        }
    }
}
