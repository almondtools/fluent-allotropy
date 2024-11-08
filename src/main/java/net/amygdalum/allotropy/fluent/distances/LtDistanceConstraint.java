package net.amygdalum.allotropy.fluent.distances;

import java.util.function.Predicate;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record LtDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static LtDistanceConstraint lt(Distance... distance) {
        return new LtDistanceConstraint(Precision.exact(), distance[0]);
    }

    @Override
    public Predicate<Distance> inContext(AssertionContext context) {
        return dist -> precision.lt(dist.pixels(context), distance.pixels(context));
    }

    @Override
    public LtDistanceConstraint withPrecision(Precision precision) {
        return new LtDistanceConstraint(precision, distance);
    }

    @Override
    public String describeIn(AssertionContext context) {
        return "< " + distance.describeIn(context);
    }
}
