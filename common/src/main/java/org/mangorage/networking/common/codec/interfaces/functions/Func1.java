package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func1<R, T1> {
    R apply(T1 t1);
}
