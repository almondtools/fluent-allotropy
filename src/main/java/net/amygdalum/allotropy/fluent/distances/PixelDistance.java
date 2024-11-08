package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record PixelDistance(double pixels) implements Distance {

    @Override
    public double pixels(AssertionContext context) {
        return pixels;
    }

    public String describeIn(AssertionContext context) {
        return value(pixels) + "px";
    }

}
