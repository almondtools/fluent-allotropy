package net.amygdalum.allotropy.fluent.distances;

import java.text.NumberFormat;
import java.util.Locale;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.elements.Bounds;

public record PercentageDistance(double percent, Dimension dimension, Bounds bounds) implements Distance {

    private static final NumberFormat FORMAT = numberFormat();

    private static NumberFormat numberFormat() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    @Override
    public double pixels() {
        return (percent / 100) * bounds.size(dimension);
    }

    public String toString() {
        return FORMAT.format(percent) + "% of the " + dimension.dimensionLabel() + " of " + bounds;
    }

}
