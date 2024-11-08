package net.amygdalum.allotropy.fluent.distances;

import java.text.NumberFormat;
import java.util.Locale;

public record PercentageDistance(double percent) implements Distance {

    private static final NumberFormat FORMAT = numberFormat();

    private static NumberFormat numberFormat() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    @Override
    public double pixels(AssertionContext context) {
        return (percent / 100) * context.bounds().size(context.dimension());
    }

    @Override
    public String describeIn(AssertionContext context) {
        return FORMAT.format(percent) + "% of the " + context.dimension().dimensionLabel() + " of " + context.bounds();
    }

}
