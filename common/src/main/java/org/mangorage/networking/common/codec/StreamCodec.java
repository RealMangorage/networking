package org.mangorage.networking.common.codec;

import io.netty.buffer.Unpooled;
import org.mangorage.networking.common.objects.MainObject;
import org.mangorage.networking.common.objects.SubObject;
import org.mangorage.networking.common.util.SimpleByteBuf;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mangorage.networking.common.codec.ByteBufCodecs.INT;
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

    static <B extends SimpleByteBuf, T> Builder<B, T> builder(Class<T> tClass) {
        return new Builder<>(tClass);
    }

    static <B extends SimpleByteBuf, T> Builder<B, T> builder() {
        return builder(null);
    }

    class Builder<B extends SimpleByteBuf, T> {
        private final List<FieldCodec<B, ?, T>> fieldCodecs = new LinkedList<>();
        private Function<Params, T> constructor;
        @Nullable
        private Class<T> tClass;

        private Builder(Class<T> tClass) {
            this.tClass = tClass;
        }

        public <S> Builder<B, T> field(StreamCodec<B, S> codec, Function<T, S> getter) {
            fieldCodecs.add(new FieldCodec<>(codec, getter));
            return this;
        }

        // Use to get around needing reflections.
        public Builder<B, T> apply(Function<Params, T> constructor) {
            this.constructor = constructor;
            return this;
        }

        public StreamCodec<B, T> build() {
            // Use Reflections if we dont have a constructor defined, but a class defined.
            if (tClass != null && constructor == null) {
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
            private int index = 0;

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

            public <T> T get() {
                index++;
                return get(index - 1);
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
        var STREAM_CODEC = StreamCodec.builder(MainObject.class)
                .field(INT, MainObject::getAmount)
                .field(SubObject.STREAM_CODEC, MainObject::getSubObject)
                .build();


        SimpleByteBuf byteBuf = new SimpleByteBuf(Unpooled.buffer());
        STREAM_CODEC.encode(byteBuf, new MainObject(10, new SubObject("Place Holder!")));

        var result = STREAM_CODEC.decode(byteBuf);
        var a =1;
    }
}
