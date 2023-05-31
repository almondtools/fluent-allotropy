package net.amygdalum.allotropy.fluent.precision;

public record PixelPrecision(double units) implements Precision {

    @Override
    public boolean lt(double s, double o) {
        return s < o + units;
    }

    @Override
    public boolean le(double s, double o) {
        return s <= o + units;
    }

    @Override
    public boolean eq(double s, double o) {
        return -units <= s - o
            && s - o <= units;
    }

    @Override
    public boolean ge(double s, double o) {
        return s >= o - units;
    }

    @Override
    public boolean gt(double s, double o) {
        return s > o - units;
    }

    @Override
    public String description() {
        return "~" + units + "px";
    }

}
