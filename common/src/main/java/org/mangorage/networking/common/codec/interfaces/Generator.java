package org.mangorage.networking.common.codec.interfaces;

import java.nio.file.Path;

public class Generator {
    public static void main(String[] args) {
        var corePath = Path.of("common\\src\\main\\java\\org\\mangorage\\networking\\common\\codec\\interfaces").toAbsolutePath();
        int amount = 21;
        int from = 20;
        GeneratorFuncs.generateFuncInterfaces(corePath.resolve("functions"), amount, from);
        GeneratorBuilders.generateBuilderClasses(corePath.resolve("builders"), amount, from);
    }
}
