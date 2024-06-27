package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func2<R, T1, T2> {
    R apply(T1 t1, T2 t2);
}
