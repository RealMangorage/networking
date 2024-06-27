package org.mangorage.networking.common.codec.interfaces;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.codec.ByteBufCodecs;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.builders.Builder;
import org.mangorage.networking.common.util.SimpleByteBuf;
import java.util.function.Function;

public class RecordStreamCodecBuilder {

    public static <B, R> StreamCodec<B, R> of(Function<Builder<B, R>, StreamCodec<B, R>> function) {
        return function.apply(new Builder<>() {});
    }

    public static <B, R> Builder<B, R> ofBuilder() {
        return new Builder<B, R>() {};
    }


    public static void main(String[] args) {
        record MainObject(
                int t1,
                int t2,
                int t3,
                int t4,
                int t5,
                int t6,
                int t7,
                int t8,
                int t9,
                int t10
        ) {}

        StreamCodec<SimpleByteBuf, MainObject> CODEC = of(inst -> inst
                .field(ByteBufCodecs.INT, MainObject::t1)
                .field(ByteBufCodecs.INT, MainObject::t2)
                .field(ByteBufCodecs.INT, MainObject::t3)
                .field(ByteBufCodecs.INT, MainObject::t4)
                .field(ByteBufCodecs.INT, MainObject::t5)
                .field(ByteBufCodecs.INT, MainObject::t6)
                .field(ByteBufCodecs.INT, MainObject::t7)
                .field(ByteBufCodecs.INT, MainObject::t8)
                .field(ByteBufCodecs.INT, MainObject::t9)
                .field(ByteBufCodecs.INT, MainObject::t10)
                .apply(MainObject::new)
        );

        SimpleByteBuf buf = new SimpleByteBuf(Unpooled.buffer());
        MainObject mainObject = new MainObject(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        CODEC.encode(buf, mainObject);

        var result = CODEC.decode(buf);
        var a = 1;

    }
}
