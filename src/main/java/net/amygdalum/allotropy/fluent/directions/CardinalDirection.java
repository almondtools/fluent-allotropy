package net.amygdalum.allotropy.fluent.directions;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;

public enum CardinalDirection implements Direction {
    N("top", VERTICAL), E("right", HORIZONTAL), S("bottom", VERTICAL), W("left", HORIZONTAL);

    private String label;
    private Dimension dimension;

    private CardinalDirection(String label, Dimension dimension) {
        this.label = label;
        this.dimension = dimension;
    }

    public String label() {
        return label;
    }

    public Dimension dimension() {
        return dimension;
    }

}
