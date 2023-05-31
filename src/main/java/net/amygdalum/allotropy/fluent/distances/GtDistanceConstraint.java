package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record GtDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static GtDistanceConstraint gt(Distance... distance) {
        return new GtDistanceConstraint(exact(), distance[0]);
    }

    @Override
    public boolean test(Distance dist) {
        return precision.gt(dist.pixels(), distance.pixels());
    }

    @Override
    public GtDistanceConstraint withPrecision(Precision precision) {
        return new GtDistanceConstraint(precision, distance);
    }

    @Override
    public String description() {
        return "> " + distance;
    }

}
