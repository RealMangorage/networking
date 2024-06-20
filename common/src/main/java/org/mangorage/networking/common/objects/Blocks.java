package org.mangorage.networking.common.objects;

import org.mangorage.networking.common.registry.Registries;
import org.mangorage.networking.common.registry.core.Holder;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.ResourceKey;

public class Blocks {
    public static final Holder<Block> TEST_BLOCK = Registry.registerForHolder(Registries.BLOCKS, new ResourceKey("test", "air"), new Block());

    public static void init() {}
}
