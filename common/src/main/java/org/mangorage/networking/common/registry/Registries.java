package org.mangorage.networking.common.registry;

import org.mangorage.networking.common.objects.Block;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.RegistryKey;
import org.mangorage.networking.common.registry.core.ResourceKey;

public class Registries {
    public static final RegistryKey<Registry<Registry<?>>> ROOT = createRegistryKey("root");
    public static final RegistryKey<Registry<Block>> BLOCKS = createRegistryKey("block");

    private static <T> RegistryKey<Registry<T>> createRegistryKey(String id) {
        return new RegistryKey<>(
                new ResourceKey("mango", id)
        );
    }

    public static void init() {}
}
