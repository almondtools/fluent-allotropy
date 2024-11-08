package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import java.util.function.Predicate;

import net.amygdalum.allotropy.fluent.precision.Precision;

public record BetweenDistanceConstraint(Precision precision, Distance from, Distance to) implements DistanceConstraint {

    public static BetweenDistanceConstraint between(Distance... distances) {
        return new BetweenDistanceConstraint(exact(), distances[0], distances[1]);
    }

    @Override
    public Predicate<Distance> inContext(AssertionContext context) {
        return dist -> precision.le(from.pixels(context), dist.pixels(context))
            && precision.ge(to.pixels(context), dist.pixels(context));
    }

    @Override
    public BetweenDistanceConstraint withPrecision(Precision precision) {
        return new BetweenDistanceConstraint(precision, from, to);
    }

    @Override
    public String describeIn(AssertionContext context) {
        return "between " + from.describeIn(context) + " and " + to.describeIn(context);
    }

}
