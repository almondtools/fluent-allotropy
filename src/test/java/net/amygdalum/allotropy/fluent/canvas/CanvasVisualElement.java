package net.amygdalum.allotropy.fluent.canvas;

import java.util.Optional;

import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public record CanvasVisualElement(Bounds bounds) implements VisualElement, AsVisualElement<CanvasVisualElement> {

    public CanvasVisualElement(int left, int top, int right, int bottom) {
        this(new Bounds(left, top, right, bottom));
    }

    @Override
    public Bounds bounds() {
        return bounds;
    }

    @Override
    public Optional<VisualElement> parent() {
        return Optional.empty();
    }

    @Override
    public CanvasVisualElement asVisualElement() {
        return this;
    }

    public String toString() {
        return bounds.toString();
    }

}
