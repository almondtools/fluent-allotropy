package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import java.util.function.Predicate;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record GtDistanceConstraint(Precision precision, Distance distance) implements DistanceConstraint {

    public static GtDistanceConstraint gt(Distance... distance) {
        return new GtDistanceConstraint(exact(), distance[0]);
    }

    @Override
    public Predicate<Distance> inContext(AssertionContext context) {
        return dist -> precision.gt(dist.pixels(context), distance.pixels(context));
    }

    @Override
    public GtDistanceConstraint withPrecision(Precision precision) {
        return new GtDistanceConstraint(precision, distance);
    }

    @Override
    public String describeIn(AssertionContext context) {
        return "> " + distance.describeIn(context);
    }

}
