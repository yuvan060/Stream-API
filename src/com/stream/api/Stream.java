    package com.stream.api;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.function.Consumer;
    import java.util.function.Function;
    import java.util.function.Predicate;
    import java.lang.RuntimeException;

    public class Stream<T> {
        private final List<T> source;
        private final List<Function<Object, Object>> operations;
        private Stream<?> downStream;

        public Stream(List<T> source) {
            this(source, new ArrayList<>());
        }

        public Stream(List<T> source, List<Function<Object, Object>> operations) {
            this.source = source;
            this.operations = operations;
        }

        public Stream<T> filter(Predicate<T> predicate) {
            isConsumed();
            List<Function<Object, Object>> newOperations =  new ArrayList<>(operations);
            Function<Object, Object> predicateObj = (item) -> predicate.test((T)item) ? item : null;
            newOperations.add(predicateObj);
            this.downStream =  new Stream<>(source, newOperations);
            return (Stream<T>) this.downStream;
        }

        public <R> Stream<R> map(Function<T, R> function) {
            isConsumed();
            List<Function<Object, Object>> newOperations = new ArrayList<>(operations);
            Function<Object, Object> funObj = (item) -> function.apply((T)item);
            newOperations.add(funObj);
            this.downStream = new Stream<>(source, newOperations);
            return (Stream<R>) this.downStream;
        }

        public void forEach(Consumer<Object> consumer) {
            isConsumed();
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

        private void isConsumed() {
            if(this.downStream != null) {
                throw new RuntimeException("Stream can not be consumed again...");
            }
        }
    }
