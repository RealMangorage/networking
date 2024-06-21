package org.mangorage.networking.common.registry;

import org.mangorage.networking.common.block.Block;
import org.mangorage.networking.common.registry.core.SimpleRegistry;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.RegistryKey;

public class BuiltInRegistries {
    public static final Registry<Registry<?>> ROOT = SimpleRegistry.of(Registries.ROOT);
    public static final Registry<Block> BLOCKS = createRegistry(Registries.BLOCKS);

    private static <T, R extends Registry<T>> Registry<T> createRegistry(RegistryKey<R> registryKey) {
        SimpleRegistry<T> registry = SimpleRegistry.of(registryKey);
        Registry.registerRegistry(registryKey.location(), registry);
        return registry;
    }

    public static void init() {}

    public static void freeze() {
        ROOT.freeze();
        ROOT.getEntries().forEach((k, r) -> r.get().freeze());
    }
}
