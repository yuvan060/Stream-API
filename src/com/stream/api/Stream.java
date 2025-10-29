package com.stream.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T> {
    private final List<T> source;
    private final List<Function<Object, Object>> operations;
    private boolean isConsumed;

    public Stream(List<T> source) {
        this(source, new ArrayList<>());
    }

    public Stream(List<T> source, List<Function<Object, Object>> operations) {
        this.source = source;
        this.operations = operations;
        this.isConsumed = false;
    }

    public Stream<T> filter(Predicate<T> predicate) {
        if(isConsumed) {
            throw new RuntimeException("Streams cannot be consumed again");
        }
        this.isConsumed = true;
        List<Function<Object, Object>> newOperations =  operations;
        Function<Object, Object> predicateObj = (item) -> predicate.test((T)item) ? item : null;
        newOperations.add(predicateObj);
        return new Stream<>(source, newOperations);
    }

    public void forEach(Consumer<Object> consumer) {
        if(isConsumed) {
            throw new RuntimeException("Streams cannot be consumed again");
        }
        this.isConsumed = true;
        for(T i : source) {
            Object current = i;
            for(Function<Object, Object> opr : this.operations) {
                current = opr.apply(current);
                if(current == null) {
                    break;
                }
            }
            if(current != null)
                consumer.accept(current);
        }
    }
}
