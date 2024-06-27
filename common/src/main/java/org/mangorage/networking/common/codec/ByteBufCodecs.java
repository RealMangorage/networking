package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.codec.interfaces.RecordStreamCodecBuilder;
import org.mangorage.networking.common.util.SimpleByteBuf;

public class ByteBufCodecs {
    public static final StreamCodec<SimpleByteBuf, Integer> INT = StreamCodec.of(SimpleByteBuf::writeInt, SimpleByteBuf::readInt);
    public static final StreamCodec<SimpleByteBuf, String> STRING = StreamCodec.of(SimpleByteBuf::writeString, SimpleByteBuf::readString);
    public static final StreamCodec<SimpleByteBuf, ? extends Enum<?>> ENUM = RecordStreamCodecBuilder.of(inst -> inst
            .field(STRING, e -> e.getClass().getName())
            .field(INT, Enum::ordinal)
            .apply((clazz, oridinal) -> {
                try {
                    var resolvedClass = Class.forName(clazz);
                    if (Enum.class.isAssignableFrom(resolvedClass)) {
                        var values = (Enum<?>[]) resolvedClass.getMethod("values").invoke(null);
                        if (oridinal < 0)
                            return values[0];
                        if (oridinal > values.length - 1)
                            return values[0];
                        return values[oridinal];
                    } else {
                        throw new RuntimeException("Cannot get Enum due to %s not being an Enum".formatted(clazz));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
    );
    public static final StreamCodec<SimpleByteBuf, ? extends Enum<?>> ENUM_STRICT = RecordStreamCodecBuilder.of(inst -> inst
            .field(STRING, e -> e.getClass().getName())
            .field(INT, Enum::ordinal)
            .apply((clazz, oridinal) -> {
                try {
                    var resolvedClass = Class.forName(clazz);
                    if (Enum.class.isAssignableFrom(resolvedClass)) {
                        var values = (Enum<?>[]) resolvedClass.getMethod("values").invoke(null);
                        return values[oridinal];
                    } else {
                        throw new RuntimeException("Cannot get Enum due to %s not being an Enum".formatted(clazz));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
    );
}
