package net.insomniacs.nucleus.api.utils;

public sealed interface TypeContainer<T> {
    record Single<T>(T element) implements TypeContainer<T> { }
    record Multi<T>(T... elements) implements TypeContainer<T> {
        @SafeVarargs
        public Multi {
        }
    }
}
