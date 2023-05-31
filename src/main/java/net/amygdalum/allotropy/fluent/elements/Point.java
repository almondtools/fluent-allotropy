package net.amygdalum.allotropy.fluent.elements;

public record Point(int x, int y) {

    public double distanceTo(Point that) {
        int dx = this.x - that.x;
        int dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
