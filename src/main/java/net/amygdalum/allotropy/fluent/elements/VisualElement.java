package net.amygdalum.allotropy.fluent.elements;

import java.util.Optional;

public interface VisualElement {
    Bounds bounds();

    Optional<VisualElement> parent();
}
