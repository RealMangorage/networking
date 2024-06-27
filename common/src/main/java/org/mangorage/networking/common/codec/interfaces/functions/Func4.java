package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func4<R, T1, T2, T3, T4> {
    R apply(T1 t1, T2 t2, T3 t3, T4 t4);
}
