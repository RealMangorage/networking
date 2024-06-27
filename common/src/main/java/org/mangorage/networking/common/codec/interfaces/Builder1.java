package org.mangorage.networking.common.codec.interfaces;


import org.mangorage.networking.common.codec.StreamCodec;
import org.mangorage.networking.common.codec.interfaces.functions.Func1;

public class Builder1 {
    public interface IBuilder1<Buf, R, A> {
        StreamCodec<Buf, R> apply(Func1<R, A> function);
    }
}

