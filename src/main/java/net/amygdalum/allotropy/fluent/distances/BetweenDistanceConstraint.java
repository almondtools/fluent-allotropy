package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record BetweenDistanceConstraint(Precision precision, Distance from, Distance to) implements DistanceConstraint {

    public static BetweenDistanceConstraint between(Distance... distances) {
        return new BetweenDistanceConstraint(exact(), distances[0], distances[1]);
    }

    @Override
    public boolean test(Distance dist) {
        return precision.le(from.pixels(), dist.pixels())
            && precision.ge(to.pixels(), dist.pixels());
    }

    @Override
    public BetweenDistanceConstraint withPrecision(Precision precision) {
        return new BetweenDistanceConstraint(precision, from, to);
    }

    @Override
    public String description() {
        return "between " + from + " and " + to;
    }

}
