package org.mangorage.networking.common.objects;

import org.mangorage.networking.common.codec.ByteBufCodecs;
import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;

public record SubObject(String info) {
    public final static StreamCodec<SimpleByteBuf, SubObject> STREAM_CODEC = StreamCodec.builder(SubObject.class)
            .field(ByteBufCodecs.STRING, SubObject::info)
            .build();
}
