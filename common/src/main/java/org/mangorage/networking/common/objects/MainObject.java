package org.mangorage.networking.common.objects;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;

public class MainObject {
    private static void write(SimpleByteBuf byteBuf, MainObject mainObject) {
        byteBuf.writeInt(mainObject.getAmount());
        SubObject.STREAM_CODEC.encode(byteBuf, mainObject.getSubObject());
    }

    private static MainObject read(SimpleByteBuf byteBuf) {
        return new MainObject(
                byteBuf.readInt(),
                SubObject.STREAM_CODEC.decode(byteBuf)
        );
    }

    public static final StreamCodec<SimpleByteBuf, MainObject> STREAM_CODEC = StreamCodec.of(MainObject::write, MainObject::read);

    public final int amount;
    public final SubObject subObject;

    public MainObject(int amount, SubObject subObject) {
        this.amount = amount;
        this.subObject = subObject;
    }

    public int getAmount() {
        return amount;
    }

    public SubObject getSubObject() {
        return subObject;
    }
}
