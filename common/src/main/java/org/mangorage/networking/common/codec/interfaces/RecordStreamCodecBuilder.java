package org.mangorage.networking.common.codec.interfaces;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.codec.ByteBufCodecs;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;


import java.util.function.Function;

@SuppressWarnings("all")
public class RecordStreamCodecBuilder {

    public static <B, R> StreamCodec<B, R> of(Function<Builder<B, R>, StreamCodec<B, R>> function) {
        return function.apply(new Builder<>() {});
    }

    public static <B, R> Builder<B, R> ofBuilder() {
        return new Builder<B, R>() {};
    }


    public static void main(String[] args) {
        record MainObject(int a) {}

        StreamCodec<SimpleByteBuf, MainObject> CODEC = of(inst -> inst
                .field(ByteBufCodecs.INT, MainObject::a)
                .apply(MainObject::new)
        );

        SimpleByteBuf buf = new SimpleByteBuf(Unpooled.buffer());
        MainObject mainObject = new MainObject(100);

        CODEC.encode(buf, mainObject);

        var result = CODEC.decode(buf);
        var a = 1;

    }
}
