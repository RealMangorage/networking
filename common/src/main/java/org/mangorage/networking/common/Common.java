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

        Holder<? extends Block> TEST_BLOCK = Blocks.TEST_BLOCK;
        Holder<? extends Block> TEST_BLOCK_2 = Blocks.TEST_BLOCK_2;

        TEST_BLOCK.streamCodec().encode(byteBuf);
        TEST_BLOCK_2.streamCodec().encode(byteBuf);

        var holder = Holder.STREAM_CODEC.decode(byteBuf);
        System.out.println(holder.getRegistryKey());
        System.out.println(holder.getId());

        var holder2 = Holder.STREAM_CODEC.decode(byteBuf);
        System.out.println(holder2.getRegistryKey());
        System.out.println(holder2.getId());
    }
}
