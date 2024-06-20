package org.mangorage.networking.common.codec;

import org.mangorage.networking.common.util.SimpleByteBuf;

public record DeferredStream<B extends SimpleByteBuf, O>(StreamCodec<B, O> streamCodec, O object) {}
