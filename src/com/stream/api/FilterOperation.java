package com.stream.api;

import java.util.function.Predicate;

public class FilterOperation<T> implements Operation<T, T>{
    private final Predicate<T> predicate;

    public FilterOperation(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public T apply(T item) {
        return predicate.test(item) ? item : null;
    }
}
