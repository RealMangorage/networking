package org.mangorage.networking.common.codec.interfaces;



import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.functions.Func1;

import java.util.function.Function;

public abstract class Builder<Buf, R> {
    static <Buf, R> Builder<Buf, R> create() {
        return null;
    }

    public <A> Builder1.IBuilder1<Buf, R, A> field(StreamCodec<Buf, A> codecA, Function<R, A> functionA) {
        return new Impl<>(codecA, functionA);
    }

    record Impl<Buf, R, A>(
            Field<Buf, R, A> fieldA
    ) implements Builder1.IBuilder1<Buf, R, A> {

        public Impl(StreamCodec<Buf, A> codecA, Function<R, A> functionA) {
            this(new Field<>(codecA, functionA));
        }

        @Override
        public StreamCodec<Buf, R> apply(Func1<R, A> function) {
            return new StreamCodec<Buf, R>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                }
            };
        }
    }
}
