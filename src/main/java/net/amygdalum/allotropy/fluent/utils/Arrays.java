package net.amygdalum.allotropy.fluent.utils;

import java.lang.reflect.Array;
import java.util.stream.Collector;
import java.util.stream.Stream;

public final class Arrays {

    private Arrays() {
    }

    public static <T> Collector<T, ArrayBuilder<T>, T[]> toArray() {
        return Collector.<T, ArrayBuilder<T>, T[]> of(ArrayBuilder::new, ArrayBuilder::accept, ArrayBuilder::merge, ArrayBuilder::finish);
    }

    private static class ArrayBuilder<T> {
        private Stream.Builder<T> builder;
        private Class<T> clazz;

        public ArrayBuilder() {
            builder = Stream.builder();
        }

        public ArrayBuilder<T> accept(T element) {
            @SuppressWarnings("unchecked")
            Class<T> subjectClass = (Class<T>) element.getClass();
            if (clazz == null) {
                clazz = subjectClass;
            } else if (subjectClass != clazz) {
                throw new IllegalArgumentException("expecting all elements of same type, but found multiple types");
            }
            builder.add(element);
            return this;
        }

        public ArrayBuilder<T> merge(ArrayBuilder<T> that) {
            if (this.clazz == null) {
                return that;
            } else if (that.clazz == null) {
                return this;
            } else if (this.clazz != that.clazz) {
                throw new IllegalArgumentException("expecting all elements of same type, but found multiple types");
            } else {
                that.builder.build().forEach(this.builder::add);
                return this;
            }
        }

        public T[] finish() {
            return builder.build().toArray(i -> newArray(i));
        }

        @SuppressWarnings("unchecked")
        private T[] newArray(int i) {
            return (T[]) Array.newInstance(clazz, i);
        }
    }
}
