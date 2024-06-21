package org.mangorage.networking.common.registry;

import org.mangorage.networking.common.block.Block;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.RegistryKey;
import org.mangorage.networking.common.registry.core.ResourceKey;

public class Registries {
    public static final ResourceKey ROOT_KEY = ResourceKey.createWithPath("root");
    public static final RegistryKey<Registry<Registry<?>>> ROOT = createRegistryKey("root");
    public static final RegistryKey<Registry<Block>> BLOCKS = createRegistryKey("block");

    private static <T> RegistryKey<Registry<T>> createRegistryKey(String id) {
        return new RegistryKey<>(
                ROOT_KEY,
                ResourceKey.createWithPath(id)
        );
    }

    public static void init() {}
}
