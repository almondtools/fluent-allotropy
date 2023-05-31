package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record EqDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static EqDistanceConstraint eq(Distance... distance) {
        return new EqDistanceConstraint(exact(), distance[0]);
    }

    @Override
    public boolean test(Distance dist) {
        return precision.eq(distance.pixels(), dist.pixels());
    }

    @Override
    public EqDistanceConstraint withPrecision(Precision precision) {
        return new EqDistanceConstraint(precision, distance);
    }

    @Override
    public String description() {
        return "of " + distance;
    }

}
