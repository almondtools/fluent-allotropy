package net.amygdalum.allotropy.fluent.chunks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class Chunks {

    public static <T extends VisualElement> Function<T[], T[][]> window(int size) {
        return t -> {
            if (t.length < size) {
                return newArray(t, 0);
            } else {
                List<T[]> result = new ArrayList<>();
                for (int i = 0; i <= t.length - size; i++) {
                    T[] window = Arrays.copyOfRange(t, i, i + size);
                    result.add(window);
                }
                return result.toArray(i -> newArray(t, i));
            }
        };
    }

    public static <T extends VisualElement> T[][] newArray(T[] element, int i) {
        @SuppressWarnings("unchecked")
        T[][] array = (T[][]) Array.newInstance(element.getClass(), i);
        return array;
    }

}
