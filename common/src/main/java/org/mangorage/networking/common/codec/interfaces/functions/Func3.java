package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func3<R, A, B, C> {
    R apply(A a, B b, C c);
}
