package org.mangorage.networking.common.codec.interfaces.builders;

import org.mangorage.networking.common.codec.interfaces.Field;

import java.util.List;
import java.util.function.Function;

class Helper {
    static <Buf, R> void encode(Buf buf, R obj, List<Field<Buf, R, ?>> list) {
        list.forEach(f -> f.encode(buf, obj));
    }

    static class Params<Buf, R> {
        private final List<Field<Buf, R, ?>> fields;
        private final Buf buf;
        private int index = 0;

        public Params(List<Field<Buf, R, ?>> fields, Buf buf) {
            this.fields = fields;
            this.buf = buf;
        }

        @SuppressWarnings("unchecked")
        public <O> O get() {
            index++;
            var field = fields.get(index - 1);
            return (O) field.fieldCodec().decode(buf);
        }
    }

    static <Buf, R, F> R decode(Buf buf, List<Field<Buf, R, ?>> fields, F func, Function<Params<Buf, R>, R> function) {
        return function.apply(new Params<>(fields, buf));
    }
}
