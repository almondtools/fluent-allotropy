package net.amygdalum.allotropy.fluent.alignment;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;

import net.amygdalum.allotropy.fluent.alignment.Alignment.Boundary;
import net.amygdalum.allotropy.fluent.alignment.Alignment.Center;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;

public sealed interface Alignment permits Boundary, Center {

    String label();

    Dimension dimension();

    static Alignment to(CardinalDirection direction) {
        return Boundary.of(direction);
    }

    static Alignment centered(Dimension dimension) {
        return Center.of(dimension);
    }

    static enum Boundary implements Alignment {
        TOP(N, VERTICAL), RIGHT(E, HORIZONTAL), BOTTOM(S, VERTICAL), LEFT(W, HORIZONTAL);

        private CardinalDirection direction;
        private Dimension dimension;

        private Boundary(CardinalDirection direction, Dimension dimension) {
            this.direction = direction;
            this.dimension = dimension;
        }

        public static Boundary of(CardinalDirection direction) {
            return switch (direction) {
            case N -> TOP;
            case E -> RIGHT;
            case S -> BOTTOM;
            case W -> LEFT;
            };
        }

        @Override
        public String label() {
            return "at " + direction.label();
        }

        public CardinalDirection direction() {
            return direction;
        }

        public Dimension dimension() {
            return dimension;
        }

    }

    static enum Center implements Alignment {
        HORIZONTALLY(HORIZONTAL), VERTICALLY(VERTICAL);

        private Dimension dimension;

        private Center(Dimension dimension) {
            this.dimension = dimension;
        }

        public static Center of(Dimension dimension) {
            return switch (dimension) {
            case HORIZONTAL -> HORIZONTALLY;
            case VERTICAL -> VERTICALLY;
            };
        }

        @Override
        public String label() {
            return "centered";
        }

        public Dimension dimension() {
            return dimension;
        }
    }

}
