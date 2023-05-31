package net.amygdalum.allotropy.fluent.precision;

public record ProtoPrecision<T extends Precisable<T>>(double units, T assertion) {

    public T pixels() {
        assertion.withPrecision(new PixelPrecision(units));
        return assertion;
    }

}
