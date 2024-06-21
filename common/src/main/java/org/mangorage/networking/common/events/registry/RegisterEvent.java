package org.mangorage.networking.common.events.registry;

import org.mangorage.networking.common.registry.Registries;
import org.mangorage.networking.common.registry.core.Registry;

public class RegisterEvent {

    public RegisterEvent() {}

    public void registerRegistry(Registry<?> registry) {
        Registry.registerRegistry(registry.getRegistryKey().location(), registry);
    }
}
