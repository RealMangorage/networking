package org.mangorage.networking.common;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.objects.Block;
import org.mangorage.networking.common.objects.Blocks;
import org.mangorage.networking.common.registry.core.Holder;
import org.mangorage.networking.common.util.SimpleByteBuf;

public class Common {
    public static void main(String[] args) {
        Bootstrap.init();

        SimpleByteBuf byteBuf = new SimpleByteBuf(Unpooled.buffer());
        Holder<Block> TEST_BLOCK = Blocks.TEST_BLOCK;

        TEST_BLOCK.streamCodec().encode(byteBuf, TEST_BLOCK);

        var holder = Holder.STREAM_CODEC.decode(byteBuf);
        var a = 1;
    }
}
