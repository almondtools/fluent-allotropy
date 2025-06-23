package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.elements.AsVisualElement.toVisualElements;

import java.util.function.Function;

import org.openqa.selenium.NoSuchElementException;

import net.amygdalum.allotropy.fluent.elements.ChunkElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public interface Selecting<T, S> {

    S apply(T base);

    static <T extends VisualElement, S extends VisualElement> Selecting<T, S> by(Function<T, S> selector) {
        return t -> {
            try {
                S selected = selector.apply(t);
                return selected;
            } catch (NoSuchElementException e) {
                return null;
            }
        };
    }

    static <T extends VisualElement, S extends VisualElement> Selecting<T, ChunkElement<S>> all(Function<T, ?> selector) {
        return t -> {
            Object selected = selector.apply(t);
            @SuppressWarnings("unchecked")
            S[] chunk = (S[]) toVisualElements(selected);
            return new ChunkElement<>(chunk);
        };
    }

}
