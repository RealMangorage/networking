package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func2<R, A, B> {
    R apply(A a, B b);
}
