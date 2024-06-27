package org.mangorage.networking.common.codec;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.util.SimpleByteBuf;
import java.util.function.Function;

public class RecordStreamCodecBuilderBeta {
    public static <R> StreamCodec<SimpleByteBuf, R> create(Class<R> rClass, Function<Builder<SimpleByteBuf, R>, StreamCodec<SimpleByteBuf, R>> function) {
        return function.apply(new Builder<>() {});
    }

    record MainObject(int test, int test2, int test3, int test4) {}

    public static void main(String[] args) {
        var CODEC = create(MainObject.class, f -> f
                        .field(ByteBufCodecs.INT, MainObject::test)
                        .field(ByteBufCodecs.INT, MainObject::test2)
                        .field(ByteBufCodecs.INT, MainObject::test3)
                        .field(ByteBufCodecs.INT, MainObject::test4)
                        .apply(MainObject::new)
                );

        SimpleByteBuf byteBuf = new SimpleByteBuf(Unpooled.buffer());

        CODEC.encode(byteBuf, new MainObject(1000, 20, 50, 10));


        var result = CODEC.decode(byteBuf);
        var a = 1;
    }
}
