package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

public class ByteBufCodecs {
    public static final StreamCodec<SimpleByteBuf, Integer> INT = StreamCodec.of(SimpleByteBuf::writeInt, SimpleByteBuf::readInt);
    public static final StreamCodec<SimpleByteBuf, String> STRING = StreamCodec.of(SimpleByteBuf::writeString, SimpleByteBuf::readString);
}
