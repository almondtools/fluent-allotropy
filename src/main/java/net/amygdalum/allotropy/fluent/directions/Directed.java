package net.amygdalum.allotropy.fluent.directions;

public record Directed<T>(CardinalDirection direction, T subject) {

    public static Pre at(CardinalDirection direction) {
        return new Pre(direction);
    }

    public static record Pre(CardinalDirection direction) {

        public <T> Directed<T> distance(T subject) {
            return new Directed<>(direction, subject);
        }

    }
}
