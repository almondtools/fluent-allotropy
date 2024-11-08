package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import java.util.function.Predicate;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record EqDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static EqDistanceConstraint eq(Distance... distance) {
        return new EqDistanceConstraint(exact(), distance[0]);
    }

    @Override
    public Predicate<Distance> inContext(AssertionContext context) {
        return dist -> precision.eq(distance.pixels(context), dist.pixels(context));
    }

    @Override
    public EqDistanceConstraint withPrecision(Precision precision) {
        return new EqDistanceConstraint(precision, distance);
    }

    @Override
    public String describeIn(AssertionContext context) {
        return "of " + distance.describeIn(context);
    }

}
