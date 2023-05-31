package net.amygdalum.allotropy.fluent.elements;

import java.util.Map;
import java.util.Optional;

public record Viewport(int width, int height) implements VisualElement {

    public static Viewport from(Object object) {
        if (object instanceof Viewport viewport) {
            return viewport;
        } else if (object instanceof Map<?, ?> map) {
            try {
                int width = Float.valueOf(map.get("width").toString()).intValue();
                int height = Float.valueOf(map.get("height").toString()).intValue();
                return new Viewport(width, height);
            } catch (NullPointerException | IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Bounds bounds() {
        return new Bounds(0, 0, width, height);
    }

    @Override
    public Optional<VisualElement> parent() {
        return Optional.empty();
    }

}
