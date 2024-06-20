package org.mangorage.networking.common.objects;

import io.netty.buffer.ByteBuf;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;

public record SubObject(String info) {
    private static void write(SimpleByteBuf byteBuf, SubObject object) {
        byteBuf.writeString(object.info());
    }
    private static SubObject read(SimpleByteBuf byteBuf) {
        return new SubObject(byteBuf.readString());
    }

    public final static StreamCodec<SimpleByteBuf, SubObject> STREAM_CODEC = StreamCodec.of(SubObject::write, SubObject::read);
}
