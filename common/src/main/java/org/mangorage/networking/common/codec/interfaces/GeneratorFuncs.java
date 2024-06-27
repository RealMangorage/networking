package org.mangorage.networking.common.codec.interfaces;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mangorage.networking.common.codec.interfaces.GeneratorBuilders.writeStringToFile;

public class GeneratorFuncs {

    public static void generateFuncInterfaces(Path path, int depth, int from) {
        for (int i = from; i <= depth; i++) {
            var name = "Func" + i + ".java";
            var classData = generateFuncInterface(i);
            var writeTo = path.resolve(name);
            if (!Files.exists(writeTo)) {
                writeStringToFile(writeTo, classData);
            }
        }
    }

    private static String generateFuncInterface(int i) {
        StringBuilder builder = new StringBuilder();

        builder.append("package org.mangorage.networking.common.codec.interfaces.functions;\n\n")
                .append("@FunctionalInterface\n")
                .append("public interface Func").append(i).append("<R");

        for (int j = 1; j <= i; j++) {
            builder.append(", T").append(j);
        }

        builder.append("> {\n")
                .append("    R apply(");

        for (int j = 1; j <= i; j++) {
            builder.append("T").append(j).append(" t").append(j);
            if (j < i) {
                builder.append(", ");
            }
        }

        builder.append(");\n")
                .append("}\n");

        return builder.toString();
    }
}
