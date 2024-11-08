package net.amygdalum.allotropy.fluent.directions;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;

public record Directed<T>(CardinalDirection direction, T subject) {

    public Dimension dimension() {
        return direction.dimension();
    }

    public static Pre at(CardinalDirection direction) {
        return new Pre(direction);
    }

    public static record Pre(CardinalDirection direction) {

        public <T> Directed<T> distance(T subject) {
            return new Directed<>(direction, subject);
        }

    }
}
