package net.amygdalum.allotropy.fluent.directions;

public enum CardinalDirection implements Direction {
    N("top"), E("right"), S("bottom"), W("left");

    private String label;

    private CardinalDirection(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

}
