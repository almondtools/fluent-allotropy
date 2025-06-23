package net.amygdalum.allotropy.fluent.multiple;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.elements.ChunkElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public interface Selecting<T, S> {

    S[] apply(T[] base);

    static <T extends VisualElement> Selecting<T, ChunkElement<T>> window(int size) {
        return t -> {
            if (t.length < size) {
                @SuppressWarnings("unchecked")
                ChunkElement<T>[] windows = new ChunkElement[] {new ChunkElement<>(t)};
                return windows;
            } else {
                @SuppressWarnings("unchecked")
                ChunkElement<T>[] windows = new ChunkElement[t.length - size + 1];
                for (int i = 0; i < windows.length; i++) {
                    T[] elements = Arrays.copyOfRange(t, i, i + size);
                    windows[i] = new ChunkElement<>(elements);
                }
                return windows;
            }
        };
    }

    static <T extends VisualElement> Selecting<T, ChunkElement<T>> sized(int size) {
        return t -> {
            int len = (t.length + size - 1) / size;
            @SuppressWarnings("unchecked")
            ChunkElement<T>[] chunks = new ChunkElement[len];
            for (int i = 0; i < chunks.length; i++) {
                var start = i * size;
                var end = Math.min(start + size, t.length);
                chunks[i] = new ChunkElement<>(Arrays.copyOfRange(t, start, end));
            }
            return chunks;
        };
    }

    static <T extends VisualElement, S extends VisualElement> Selecting<T, S> by(Function<T, S> selector) {
        return t -> {
            @SuppressWarnings("unchecked")
            S[] selected = (S[]) new VisualElement[t.length];
            int j = 0;
            for (int i = 0; i < selected.length; i++) {
                try {
                    VisualElement selectedItem = selector.apply(t[i]);
                    if (selectedItem instanceof VisualElement) {
                        @SuppressWarnings("unchecked")
                        S selectedTypedItem = (S) selectedItem;
                        selected[j] = selectedTypedItem;
                        j++;
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
            return Arrays.copyOf(selected, j);
        };
    }

}
