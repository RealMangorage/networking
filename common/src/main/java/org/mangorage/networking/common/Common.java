package org.mangorage.networking.common;


import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.codec.ByteBufCodecs;
import org.mangorage.networking.common.codec.StreamCodec;

import org.mangorage.networking.common.codec.interfaces.RecordStreamCodecBuilder;
import org.mangorage.networking.common.util.SimpleByteBuf;

public class Common {
    public static void main(String[] args) {
        //Bootstrap.init();

        record MainObject(int a, int b, int c, int d) {}

        StreamCodec<SimpleByteBuf, MainObject> CODEC = RecordStreamCodecBuilder.of(inst -> inst
                .field(ByteBufCodecs.INT, MainObject::a)
                .field(ByteBufCodecs.INT, MainObject::b)
                .field(ByteBufCodecs.INT, MainObject::c)
                .field(ByteBufCodecs.INT, MainObject::d)
                .apply(MainObject::new)
        );

        SimpleByteBuf buf = new SimpleByteBuf(Unpooled.buffer());
        MainObject mainObject = new MainObject(100, 20, 5, 8);

        CODEC.encode(buf, mainObject);

        var result = CODEC.decode(buf);
        var a = 1;
    }
}
