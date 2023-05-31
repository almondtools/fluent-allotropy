package net.amygdalum.allotropy.fluent.directional;

import java.util.Optional;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraint;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.Precision;

public record DirectionalDistanceConstraint(Optional<CardinalDirection> direction, DistanceConstraint distance) implements Constraint<Object>, Precisable<DirectionalDistanceConstraint> {

    public static DirectionalDistanceConstraint NONE = new DirectionalDistanceConstraint(Optional.empty(), DistanceConstraint.NONE);

    public boolean test(Object constrainable) {
        if (constrainable instanceof Distance distance) {
            return this.distance.test(distance);
        } else if (constrainable instanceof CardinalDirection direction) {
            return this.direction.map(d -> d == direction).orElse(true);
        } else {
            throw new IllegalArgumentException("directional distance may not test on objects of type " + constrainable == null ? "null" : constrainable.getClass().getName());
        }
    }

    @Override
    public DirectionalDistanceConstraint withPrecision(Precision precision) {
        return new DirectionalDistanceConstraint(direction, distance.withPrecision(precision));
    }

    @Override
    public String description() {
        return distance.description() + " " + direction.map(dx -> dx.label()).orElse("any");
    }

}
