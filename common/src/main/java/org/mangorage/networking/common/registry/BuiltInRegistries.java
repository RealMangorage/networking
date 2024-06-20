package org.mangorage.networking.common.registry;

import org.mangorage.networking.common.objects.Block;
import org.mangorage.networking.common.registry.core.SimpleRegistry;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.RegistryKey;

public class BuiltInRegistries {
    public static final Registry<Registry<?>> ROOT = SimpleRegistry.of(Registries.ROOT);
    public static final Registry<Block> BLOCKS = createRegistry(Registries.BLOCKS);

    private static <T, R extends Registry<T>> Registry<T> createRegistry(RegistryKey<R> registryKey) {
        SimpleRegistry<T, R> registry = SimpleRegistry.of(registryKey);
        ROOT.register(registryKey.registry(), registry);
        return registry;
    }

    public static void init() {}
}
