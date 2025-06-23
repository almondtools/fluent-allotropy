package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.Bounds.NONE;

import java.util.Arrays;
import java.util.Optional;

public record ChunkElement<T extends VisualElement>(T[] elements) implements VisualElement, VisualElementAggregate<T> {

    @Override
    public Bounds bounds() {
        return Arrays.stream(elements)
            .map(VisualElement::bounds)
            .reduce(Bounds::extend)
            .orElse(NONE);
    }

    @Override
    public Optional<VisualElement> parent() {
        var parents = Arrays.stream(elements)
            .flatMap(element -> element.parent().stream())
            .distinct()
            .toList();
        while (parents.size() > 1) {
            parents = parents.stream()
                .flatMap(element -> element.parent().stream())
                .distinct()
                .toList();
        }
        return parents.stream().findFirst();
    }

    public String toString() {
        return Arrays.toString(elements);
    }

}
