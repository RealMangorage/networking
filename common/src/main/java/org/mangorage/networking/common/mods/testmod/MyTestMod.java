package org.mangorage.networking.common.mods.testmod;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.events.Events;
import org.mangorage.networking.common.events.registry.RegisterEvent;
import org.mangorage.networking.common.mod.EntryPoint;
import org.mangorage.networking.common.registry.core.Holder;
import org.mangorage.networking.common.util.SimpleByteBuf;

@EntryPoint
public class MyTestMod {
    public MyTestMod() {
        Events.REGISTRY_EVENT.addListener(this::onRegister);
        Events.FINISH_LOADING_EVENT.addListener(this::onFinish);
    }

    public void onRegister(RegisterEvent event) {
        event.registerRegistry(TestModRegistries.CUSTOM);
    }

    public void onFinish(Void v) {
        SimpleByteBuf byteBuf = new SimpleByteBuf(Unpooled.buffer());

        var myTest = TestModRegistries.CUSTOM_TEST;

        myTest.streamCodec().encode(byteBuf);

        var result = Holder.STREAM_CODEC.decode(byteBuf);
        if (result == null)
            throw new IllegalStateException("Failed to encode/decode");
    }
}
