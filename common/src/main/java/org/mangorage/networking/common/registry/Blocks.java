package org.mangorage.networking.common.registry;

import org.mangorage.networking.common.block.MyCustomBlock;
import org.mangorage.networking.common.registry.core.Holder;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.ResourceKey;

public class Blocks {
    public static final Holder<MyCustomBlock> TEST_BLOCK = Registry.registerForHolder(Registries.BLOCKS, new ResourceKey("test", "air"), new MyCustomBlock());
    public static final Holder<MyCustomBlock> TEST_BLOCK_2 = Registry.registerForHolder(BuiltInRegistries.BLOCKS, new ResourceKey("test", "air2"), new MyCustomBlock());

    public static void init() {}
}
