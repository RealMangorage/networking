package org.mangorage.networking.common.codec;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.objects.MainObject;
import org.mangorage.networking.common.objects.SubObject;
import org.mangorage.networking.common.util.SimpleByteBuf;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mangorage.networking.common.codec.ByteBufCodecs.INT;
import static org.mangorage.networking.common.codec.ByteBufCodecs.STRING;

public interface StreamCodec<B, T> extends StreamEncoder<B, T>, StreamDecoder<B, T> {

    static <B extends SimpleByteBuf, T> StreamCodec<B, T> of(StreamEncoder<B, T> encoder, StreamDecoder<B, T> decoder) {
        return new StreamCodec<B, T>() {
            @Override
            public void encode(B byteBuf, T object) {
                encoder.encode(byteBuf, object);
            }

            @Override
            public T decode(B byteBuf) {
                return decoder.decode(byteBuf);
            }
        };
    }

    static <B extends SimpleByteBuf, T> Builder<B, T> builder() {
        return new Builder<>();
    }

    class Builder<B extends SimpleByteBuf, T> {
        private final List<FieldCodec<B, ?, T>> fieldCodecs = new LinkedList<>();
        private Function<Params, T> constructor;
        @Nullable
        private Class<T> tClass;

        public <S> Builder<B, T> field(StreamCodec<B, S> codec, Function<T, S> getter) {
            fieldCodecs.add(new FieldCodec<>(codec, getter));
            return this;
        }

        public Builder<B, T> apply(Function<Params, T> constructor) {
            this.constructor = constructor;
            return this;
        }

        public Builder<B, T> apply(Class<T> tClass) {
            this.tClass = tClass;
            return this;
        }

        public StreamCodec<B, T> build() {
            if (tClass != null) {
                final Class<T> tClass1 = tClass;
                this.constructor = p -> {
                    try {
                        return tClass1.getConstructor(p.getTypes()).newInstance(p.getObjects());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                };
            }

            if (constructor == null)
                throw new IllegalStateException("Cannot build without .apply");

            return new BuiltCodec<>(List.copyOf(fieldCodecs), constructor, tClass);
        }

        record BuiltCodec<B extends SimpleByteBuf, T>(List<FieldCodec<B, ?, T>> fieldCodecs, Function<Params, T> constructor, Class<T> tClass) implements StreamCodec<B, T> {

            @Override
            public T decode(B buf) {
                Object[] params = new Object[fieldCodecs.size()];
                for (int i = 0; i < fieldCodecs.size(); i++) {
                    params[i] = fieldCodecs.get(i).codec.decode(buf);
                }
                return constructor.apply(new Params(params));
            }

            @Override
            public void encode(B buf, T object) {
                for (FieldCodec<B, ?, T> fieldCodec : fieldCodecs) {
                    fieldCodec.codec.encode(buf, fieldCodec.getter.apply(object));
                }
            }
        }

        @SuppressWarnings("unchecked")
        private static class FieldCodec<B, S, T> {
            final StreamCodec<B, Object> codec;
            final Function<T, Object> getter;

            FieldCodec(StreamCodec<B, S> codec, Function<T, S> getter) {
                this.codec = (StreamCodec<B, Object>) codec;
                this.getter = (Function<T, Object>) getter;
            }
        }

        public static class Params {
            private final Object[] object;
            private final Class<?>[] types;

            private Params(Object[] object) {
                this.object = object;
                this.types = Stream.of(object)
                        .map(Object::getClass)
                        .toArray(Class[]::new);
            }

            @SuppressWarnings("unchecked")
            public <T> T get(int index) {
                return (T) object[index];
            }

            public Class<?>[] getTypes() {
                return types;
            }

            public Object[] getObjects() {
                return object;
            }
        }
    }

    public static void main(String[] args) {
        var STREAM_CODEC = StreamCodec.<SimpleByteBuf, MainObject>builder()
                .field(INT, MainObject::getAmount)
                .field(
                        StreamCodec.<SimpleByteBuf, SubObject>builder()
                                .field(STRING, SubObject::info)
                                .apply(SubObject.class)
                                .build()
                        ,
                        MainObject::getSubObject
                )
                .apply(MainObject.class)
                .build();


        SimpleByteBuf byteBuf = new SimpleByteBuf(Unpooled.buffer());

        STREAM_CODEC.encode(byteBuf, new MainObject(10, new SubObject("Asshole!")));
        var result = STREAM_CODEC.decode(byteBuf);
        var a =1;
    }
}
