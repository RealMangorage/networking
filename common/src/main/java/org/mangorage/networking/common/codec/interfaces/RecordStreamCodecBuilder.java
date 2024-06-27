package org.mangorage.networking.common.codec.interfaces;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.builders.Builder;
import java.util.function.Function;

public class RecordStreamCodecBuilder {

    public static <B, R> StreamCodec<B, R> of(Function<Builder<B, R>, StreamCodec<B, R>> function) {
        return function.apply(new Builder<>() {});
    }

    public static <B, R> Builder<B, R> ofBuilder() {
        return new Builder<B, R>() {};
    }
}
