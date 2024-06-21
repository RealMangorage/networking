package org.mangorage.networking.common.mods.testmod;

import org.mangorage.networking.common.registry.Registries;
import org.mangorage.networking.common.registry.core.Holder;
import org.mangorage.networking.common.registry.core.Registry;
import org.mangorage.networking.common.registry.core.RegistryKey;
import org.mangorage.networking.common.registry.core.ResourceKey;
import org.mangorage.networking.common.registry.core.SimpleRegistry;

public class TestModRegistries {
    public static final RegistryKey<Registry<MyTestObject>> CUSTOM_KEY = createRegistryKey("test");
    public static final Registry<MyTestObject> CUSTOM = SimpleRegistry.of(CUSTOM_KEY);

    public static final Holder<MyTestObject> CUSTOM_TEST = CUSTOM.register(key("test"), new MyTestObject());

    public static ResourceKey key(String id) {
        return ResourceKey.createWithNamespaceAndPath("testmod", id);
    }


    public static <T> RegistryKey<Registry<T>> createRegistryKey(String id) {
        return new RegistryKey<>(
                Registries.ROOT_KEY,
                ResourceKey.createWithNamespaceAndPath("testmod", id)
        );
    }
}
