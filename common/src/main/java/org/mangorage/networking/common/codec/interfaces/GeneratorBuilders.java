package org.mangorage.networking.common.codec.interfaces;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneratorBuilders {
    public static void writeStringToFile(Path path, String content) {
        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateBuilderClasses(Path path, int depth, int from) {
        for (int i = from; i <= depth; i++) {
            var name = "Builder" + i + ".java";
            var classData = generateBuilderClass(i);
            var writeTo = path.resolve(name);
            if (!Files.exists(writeTo) || i == from) {
                writeStringToFile(writeTo, classData);
            }
        }
    }


    private static String generateBuilderClass(int i) {
        StringBuilder builder = new StringBuilder();
        String fieldParams = generateFieldParams(i);

        builder.append("package org.mangorage.networking.common.codec.interfaces.builders;\n\n")
                .append("import org.mangorage.networking.common.codec.StreamCodec;\n")
                .append("import org.mangorage.networking.common.codec.interfaces.Field;\n")
                .append("import org.mangorage.networking.common.codec.interfaces.functions.Func").append(i).append(";\n")
                .append("import java.util.function.Function;\n\n")
                .append("abstract class Builder").append(i).append(" {\n")
                .append("    public interface Builder<Buf, R").append(fieldParams).append("> {\n")
                .append("        <T").append(i + 1).append("> Builder").append(i + 1).append(".Builder<Buf, R").append(fieldParams).append(", T").append(i + 1).append("> field(StreamCodec<Buf, T").append(i + 1).append("> codec, Function<R, T").append(i + 1).append("> getter);\n")
                .append("        StreamCodec<Buf, R> apply(Func").append(i).append("<R").append(fieldParams).append("> function);\n")
                .append("    }\n\n")
                .append("    record Impl<Buf, R").append(fieldParams).append(">(\n")
                .append(generateFieldDeclarations(i))
                .append("    ) implements Builder<Buf, R").append(fieldParams).append("> {\n\n")
                .append("        public Impl(").append(generateConstructorParams(i)).append(") {\n")
                .append(generateConstructorAssignments(i))
                .append("        }\n\n")
                .append("        @Override\n")
                .append("        public <T").append(i + 1).append("> Builder").append(i + 1).append(".Builder<Buf, R").append(fieldParams).append(", T").append(i + 1).append("> field(StreamCodec<Buf, T").append(i + 1).append("> codec, Function<R, T").append(i + 1).append("> getter) {\n")
                .append("            return new Builder").append(i + 1).append(".Impl<>(");

        // Generate field assignments
        for (int j = 1; j <= i; j++) {
            builder.append("fieldT").append(j).append(j < i ? ", " : "");
        }

        builder.append(", new Field<>(codec, getter)\n")
                .append("            );\n")
                .append("        }\n\n")
                .append("        @Override\n")
                .append("        public StreamCodec<Buf, R> apply(Func").append(i).append("<R").append(fieldParams).append("> function) {\n")
                .append("            return new StreamCodec<>() {\n")
                .append("                @Override\n")
                .append("                public R decode(Buf buf) {\n")
                .append("                    return function.apply(\n")
                .append(generateFieldDecodings(i))
                .append("                    );\n")
                .append("                }\n\n")
                .append("                @Override\n")
                .append("                public void encode(Buf buf, R object) {\n")
                .append(generateFieldEncodings(i))
                .append("                }\n")
                .append("            };\n")
                .append("        }\n")
                .append("    }\n")
                .append("}\n");

        return builder.toString();
    }

    private static String generateFieldDeclarations(int i) {
        StringBuilder fields = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            fields.append("        Field<Buf, R, T").append(j).append("> fieldT").append(j).append(j < i ? ",\n" : "\n");
        }
        return fields.toString();
    }

    private static String generateConstructorParams(int i) {
        StringBuilder params = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            params.append("Field<Buf, R, T").append(j).append("> fieldT").append(j).append(j < i ? ", " : "");
        }
        return params.toString();
    }

    private static String generateConstructorAssignments(int i) {
        StringBuilder assignments = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            assignments.append("            this.fieldT").append(j).append(" = fieldT").append(j).append(";\n");
        }
        return assignments.toString();
    }

    private static String generateFieldParams(int i) {
        StringBuilder params = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            params.append(", T").append(j);
        }
        return params.toString();
    }

    private static String generateFieldConstructorParams(int i) {
        StringBuilder params = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            params.append("fieldT").append(j).append(j < i ? ", " : "");
        }
        return params.toString();
    }

    private static String generateFieldDecodings(int i) {
        StringBuilder decodings = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            decodings.append("                            fieldT").append(j).append(".decode(buf)").append(j < i ? ",\n" : "\n");
        }
        return decodings.toString();
    }

    private static String generateFieldEncodings(int i) {
        StringBuilder encodings = new StringBuilder();
        for (int j = 1; j <= i; j++) {
            encodings.append("                    fieldT").append(j).append(".encode(buf, object);\n");
        }
        return encodings.toString();
    }
}