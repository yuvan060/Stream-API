    package com.stream.api;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.function.Consumer;
    import java.util.function.Function;
    import java.util.function.Predicate;
    import java.lang.RuntimeException;

    public class Stream<T> {
        private final List<T> source;
        private final List<Operation<T,?>> operations;
        private Stream<?> downStream;

        public Stream(List<T> source) {
            this(source, new ArrayList<>());
        }

        public Stream(List<T> source, List<Operation<T, ?>> operations) {
            this.source = source;
            this.operations = operations;
        }

        public Stream<T> filter(Predicate<T> predicate) {
            isConsumed();
            List<Operation<T,?>> newOperations =  new ArrayList<>(operations);
            newOperations.add(new FilterOperation<>(predicate));
            this.downStream =  new Stream<>(source, newOperations);
            return (Stream<T>) this.downStream;
        }

        public <R> Stream<R> map(Function<T, R> function) {
            isConsumed();
            List<Operation<T,?>> newOperations = new ArrayList<>(operations);
            newOperations.add(new MapOperation<>(function));
            this.downStream = new Stream<>(source, newOperations);
            return (Stream<R>) this.downStream;
        }

        public void forEach(Consumer<Object> consumer) {
            isConsumed();
            for(T i : source) {
                Object current = i;
                for(Operation opr : this.operations) {
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

