package com.stream.api;

import java.util.function.Function;

public class MapOperation<T, R> implements Operation<T, R> {
    private final Function<T, R> mapper;

    public MapOperation(Function<T, R> mapper) {
        this.mapper = mapper;
    }

    @Override
    public R apply(T item) {
        return mapper.apply(item);
    }
}
