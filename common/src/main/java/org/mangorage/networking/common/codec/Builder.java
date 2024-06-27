package org.mangorage.networking.common.codec;

import java.util.function.Function;

public interface Builder<Buf, R> {

    default <A> Fields1<Buf, R, A> field(StreamCodec<Buf, A> codecA, Function<R, A> functionA) {
        return new BuilderImpl.Fields1Impl<>(codecA, functionA);
    }

    interface Fields1<Buf, R, A> {
        <B> Fields2<Buf, R, A, B> field(StreamCodec<Buf, B> codecB, Function<R, B> functionB);
        StreamCodec<Buf, R> apply(Const1<R, A> function);
    }

    interface Fields2<Buf, R, A, B> {
        <C> Fields3<Buf, R, A, B, C> field(StreamCodec<Buf, C> codecC, Function<R, C> functionC);
        StreamCodec<Buf, R> apply(Const2<R, A, B> function);
    }

    interface Fields3<Buf, R, A, B, C> {
        <D> Fields4<Buf, R, A, B, C, D> field(StreamCodec<Buf, D> codecD, Function<R, D> functionD);
        StreamCodec<Buf, R> apply(Const3<R, A, B, C> function);
    }

    interface Fields4<Buf, R, A, B, C, D> {
        StreamCodec<Buf, R> apply(Const4<R, A, B, C, D> function);
    }


    interface Const1<R, A> {
        R apply(A a);
    }

    interface Const2<R, A, B> {
        R apply(A a, B b);
    }

    interface Const3<R, A, B, C> {
        R apply(A a, B b, C c);
    }

    interface Const4<R, A, B, C, D> {
        R apply(A a, B b, C c, D d);
    }
}
