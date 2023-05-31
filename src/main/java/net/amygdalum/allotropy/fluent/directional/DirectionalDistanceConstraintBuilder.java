package net.amygdalum.allotropy.fluent.directional;

import java.util.Optional;
import java.util.stream.DoubleStream;

import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraintFactory;
import net.amygdalum.allotropy.fluent.distances.DistanceFactory;

public class DirectionalDistanceConstraintBuilder {

    private DoubleStream.Builder units;
    private DistanceFactory distance;
    private DistanceConstraintFactory distanceConstraint;
    private Optional<CardinalDirection> direction;

    public DirectionalDistanceConstraintBuilder() {
        this.units = DoubleStream.builder();
        this.direction = Optional.empty();
    }

    public DirectionalDistanceConstraintBuilder addUnits(double units) {
        this.units.add(units);
        return this;
    }

    public DirectionalDistanceConstraintBuilder addResolver(DistanceFactory distance) {
        this.distance = distance;
        return this;
    }

    public DirectionalDistanceConstraintBuilder addAccumulator(DistanceConstraintFactory distanceConstraint) {
        this.distanceConstraint = distanceConstraint;
        return this;
    }

    public DirectionalDistanceConstraintBuilder setDirection(CardinalDirection direction) {
        this.direction = Optional.of(direction);
        return this;
    }

    public DirectionalDistanceConstraint build() {
        return new DirectionalDistanceConstraint(direction, distanceConstraint.from(units.build().mapToObj(u -> distance.from(u)).toArray(Distance[]::new)));
    }

}
