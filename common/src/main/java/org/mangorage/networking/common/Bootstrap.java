package org.mangorage.networking.common;

import org.mangorage.networking.common.events.Events;
import org.mangorage.networking.common.events.registry.RegisterEvent;
import org.mangorage.networking.common.mod.EntryPoint;
import org.mangorage.networking.common.registry.Blocks;
import org.mangorage.networking.common.registry.BuiltInRegistries;
import org.mangorage.networking.common.registry.Registries;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;

public class Bootstrap {
    private static boolean fired = false;

    public static void init() {
        if (fired) return;
        fired = true;


        Registries.init();
        BuiltInRegistries.init();
        Blocks.init();

        // Load Mods

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.TypesAnnotated));

        reflections.getTypesAnnotatedWith(EntryPoint.class).forEach(c -> {
            try {
                c.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        // Fire RegistryEvent // Where you create new registries and register objects to them
        Events.REGISTRY_EVENT.fire(new RegisterEvent());
        BuiltInRegistries.freeze();
        Events.FINISH_LOADING_EVENT.fire(null);
    }
}
