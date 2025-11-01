package com.stream.api;

import java.util.function.Consumer;

public class ForEachOperation<T> implements Operation<T, Void>{

    private final Consumer<T> consumer;

    public ForEachOperation(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public Void apply(T item) {
        consumer.accept(item);
        return null;
    }
}
