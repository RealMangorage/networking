package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func4<R, A, B, C, D> {
    R apply(A a, B b, C c, D d);
}
