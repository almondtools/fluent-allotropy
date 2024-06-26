package net.amygdalum.allotropy.fluent.directions;

public enum LayerRelation {
    FRONT("in front"), BEHIND("behind");

    private String label;

    private LayerRelation(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

}
