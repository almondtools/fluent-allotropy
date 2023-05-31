package net.amygdalum.allotropy.fluent.conditions;

import java.util.Optional;

import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class NonDisplayable implements VisualElement {

    @Override
    public Bounds bounds() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<VisualElement> parent() {
        return Optional.empty();
    }

}