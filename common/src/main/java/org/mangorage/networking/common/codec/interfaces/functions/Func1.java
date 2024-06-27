package org.mangorage.networking.common.codec.interfaces.functions;

@FunctionalInterface
public interface Func1<R, A> {
    R apply(A a);
}
