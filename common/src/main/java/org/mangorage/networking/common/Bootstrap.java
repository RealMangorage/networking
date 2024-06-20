package org.mangorage.networking.common;

import org.mangorage.networking.common.objects.Blocks;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.registry.Registries;

public class Bootstrap {
    private static boolean fired = false;

    public static void init() {
        if (fired) return;
        fired = true;

        Registries.init();
        BuiltInRegistries.init();
        Blocks.init();
    }
}
