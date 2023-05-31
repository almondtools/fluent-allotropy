package net.amygdalum.allotropy.fluent.dimensions;

public enum Dimension {
    HORIZONTAL("width", "horizontal"), VERTICAL("height", "vertical");

    private String dimensionLabel;
    private String label;

    private Dimension(String dimensionLabel, String label) {
        this.dimensionLabel = dimensionLabel;
        this.label = label;
    }

    public static Dimension height() {
        return VERTICAL;
    }

    public static Dimension width() {
        return HORIZONTAL;
    }

    public String dimensionLabel() {
        return dimensionLabel;
    }

    public String adLabel() {
        return label + "ly";
    }

}
