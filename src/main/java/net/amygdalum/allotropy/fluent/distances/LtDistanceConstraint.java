package net.amygdalum.allotropy.fluent.distances;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record LtDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static LtDistanceConstraint lt(Distance... distance) {
        return new LtDistanceConstraint(Precision.exact(), distance[0]);
    }

    @Override
    public boolean test(Distance dist) {
        return precision.lt(dist.pixels(), distance.pixels());
    }

    @Override
    public LtDistanceConstraint withPrecision(Precision precision) {
        return new LtDistanceConstraint(precision, distance);
    }

    @Override
    public String description() {
        return "< " + distance;
    }
}
