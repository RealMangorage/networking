package org.mangorage.networking.common.codec;

import java.util.function.Function;

public abstract class BuilderImpl {
    private record Field<B, M, T>(StreamCodec<B, T> fieldCodec, Function<M, T> mainToField) {
        public T decode(B buf) {
            return fieldCodec.decode(buf);
        }

        public void encode(B buf, M object) {
            fieldCodec.encode(buf, mainToField.apply(object));
        }
    }


    record Fields1Impl<Buf, R, A>(
            Field<Buf, R, A> fieldA
    ) implements Builder.Fields1<Buf, R, A> {

        public Fields1Impl(StreamCodec<Buf, A> codecA, Function<R, A> functionA) {
            this(new Field<>(codecA, functionA));
        }

        @Override
        public <B> Builder.Fields2<Buf, R, A, B> field(StreamCodec<Buf, B> codecB, Function<R, B> functionB) {
            return new Fields2Impl<>(fieldA, new Field<>(codecB, functionB));
        }

        @Override
        public StreamCodec<Buf, R> apply(Builder.Const1<R, A> function) {
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

    private record Fields2Impl<Buf, R, A, B>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB
    ) implements Builder.Fields2<Buf, R, A, B> {
        @Override
        public <C> Builder.Fields3<Buf, R, A, B, C> field(StreamCodec<Buf, C> codecC, Function<R, C> functionC) {
            return new Fields3Impl<>(fieldA, fieldB, new Field<>(codecC, functionC));
        }

        @Override
        public StreamCodec<Buf, R> apply(Builder.Const2<R, A, B> function) {
            return new StreamCodec<Buf, R>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf),
                            fieldB.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                    fieldB.encode(buf, object);
                }
            };
        }
    }

    private record Fields3Impl<Buf, R, A, B, C>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB,
            Field<Buf, R, C> fieldC
    ) implements Builder.Fields3<Buf, R, A, B, C> {

        @Override
        public <D> Builder.Fields4<Buf, R, A, B, C, D> field(StreamCodec<Buf, D> codecD, Function<R, D> functionD) {
            return new Fields4Impl<>(fieldA, fieldB, fieldC, new Field<>(codecD, functionD));
        }

        @Override
        public StreamCodec<Buf, R> apply(Builder.Const3<R, A, B, C> function) {
            return new StreamCodec<>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf),
                            fieldB.decode(buf),
                            fieldC.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                    fieldB.encode(buf, object);
                    fieldC.encode(buf, object);
                }
            };
        }
    }

    private record Fields4Impl<Buf, R, A, B, C, D>(
            Field<Buf, R, A> fieldA,
            Field<Buf, R, B> fieldB,
            Field<Buf, R, C> fieldC,
            Field<Buf, R, D> fieldD
    ) implements Builder.Fields4<Buf, R, A, B, C, D> {
        @Override
        public StreamCodec<Buf, R> apply(Builder.Const4<R, A, B, C, D> function) {
            return new StreamCodec<Buf, R>() {
                @Override
                public R decode(Buf buf) {
                    return function.apply(
                            fieldA.decode(buf),
                            fieldB.decode(buf),
                            fieldC.decode(buf),
                            fieldD.decode(buf)
                    );
                }

                @Override
                public void encode(Buf buf, R object) {
                    fieldA.encode(buf, object);
                    fieldB.encode(buf, object);
                    fieldC.encode(buf, object);
                    fieldD.encode(buf, object);
                }
            };
        }
    }
}
