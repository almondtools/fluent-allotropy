package net.amygdalum.allotropy.fluent.single;

import java.util.NoSuchElementException;

public record AssertContext<T>(ThreadLocal<Item<T>> context) {

    public AssertContext() {
        this(ThreadLocal.withInitial(Root::new));
    }

    public void push(T item) {
        var nextContext = context.get().push(item);
        context.set(nextContext);
    }

    public T peek() {
        var peekContext = context.get();
        return peekContext.item();
    }

    public T remove() {
        var nextContext = context.get().remove();
        context.set(nextContext);
        return nextContext.item();
    }

    public sealed interface Item<T> permits Root, Child {

        default Item<T> push(T item) {
            return new Child<>(this, item);
        }

        Item<T> remove();

        T item();

    }

    record Root<T>() implements Item<T> {

        @Override
        public Item<T> remove() {
            return this;
        }

        @Override
        public T item() {
            throw new NoSuchElementException();
        }

    }

    record Child<T>(Item<T> parent, T item) implements Item<T> {

        @Override
        public Item<T> remove() {
            return parent;
        }

        @Override
        public T item() {
            return item;
        }

    }

}
