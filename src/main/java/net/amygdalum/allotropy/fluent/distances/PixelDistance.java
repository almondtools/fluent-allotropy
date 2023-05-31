package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record PixelDistance(double pixels) implements Distance {

    public String toString() {
        return value(pixels) + "px";
    }

}
