package net.amygdalum.allotropy.fluent.distances;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.elements.Bounds;

public record AssertionContext(List<Object> parts) {
    public static AssertionContext ctx() {
        return new AssertionContext(new ArrayList<>());
    }

    public AssertionContext direction(CardinalDirection direction) {
        add(direction);
        return this;
    }

    public AssertionContext direction(Optional<CardinalDirection> direction) {
        direction.ifPresent(this::direction);
        return this;
    }

    public AssertionContext dimension(Dimension dimension) {
        add(dimension);
        return this;
    }

    public AssertionContext dimension(Optional<Dimension> dimension) {
        dimension.ifPresent(this::dimension);
        return this;
    }

    public AssertionContext bounds(Bounds bounds) {
        add(bounds);
        return this;
    }

    public AssertionContext bounds(Optional<Bounds> bounds) {
        bounds.ifPresent(this::bounds);
        return this;
    }

    private void add(Object object) {
        Class<? extends Object> clazz = object.getClass();
        if (parts.stream().anyMatch(clazz::isInstance)) {
            throw new AmbiguousContextException(clazz);
        }
        parts.add(object);
    }

    public Bounds bounds() {
        return require(Bounds.class);
    }

    public Dimension dimension() {
        return fetch(Dimension.class)
            .or(() -> fetch(CardinalDirection.class)
                .map(CardinalDirection::dimension))
            .orElseThrow(missing(Dimension.class));
    }

    public CardinalDirection direction() {
        return require(CardinalDirection.class);
    }

    private <T> T require(Class<T> clazz) {
        return fetch(clazz)
            .orElseThrow(missing(clazz));
    }

    private <T> Optional<T> fetch(Class<T> clazz) {
        return parts.stream()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst();
    }

    private Supplier<MissingContextException> missing(Class<?> clazz) {
        return () -> new MissingContextException(clazz);
    }

}
