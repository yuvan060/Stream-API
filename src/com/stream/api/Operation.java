package com.stream.api;

public interface Operation<T, R> {
    R apply(T item);
}
