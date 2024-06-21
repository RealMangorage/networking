package org.mangorage.networking.common.registry.core;

import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.util.SimpleByteBuf;

public record ResourceKey(String namespace, String path) {
    public static final StreamCodec<SimpleByteBuf, ResourceKey> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(SimpleByteBuf byteBuf, ResourceKey object) {
            byteBuf.writeString(object.namespace());
            byteBuf.writeString(object.path());
        }

        @Override
        public ResourceKey decode(SimpleByteBuf byteBuf) {
            return new ResourceKey(byteBuf.readString(), byteBuf.readString());
        }
    };

    public static ResourceKey createWithPath(String path) {
        return new ResourceKey(
                Constants.GAME_ID,
                path
        );
    }

    public static ResourceKey createWithNamespaceAndPath(String name, String path) {
        return new ResourceKey(name, path);
    }

    @Override
    public String toString() {
        return "ResourceKey[%s:%s]".formatted(namespace, path);
    }
}
