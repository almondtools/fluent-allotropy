package net.amygdalum.allotropy.fluent.distances;

import java.util.stream.DoubleStream;

public class DistanceConstraintBuilder {

    private DoubleStream.Builder units;
    private DistanceFactory distance;
    private DistanceConstraintFactory distanceConstraint;

    public DistanceConstraintBuilder() {
        this.units = DoubleStream.builder();
    }

    public DistanceConstraintBuilder addUnits(double units) {
        this.units.add(units);
        return this;
    }

    public DistanceConstraintBuilder addResolver(DistanceFactory distance) {
        this.distance = distance;
        return this;
    }

    public DistanceConstraintBuilder addAccumulator(DistanceConstraintFactory distanceConstraint) {
        this.distanceConstraint = distanceConstraint;
        return this;
    }

    public DistanceConstraint build() {
        return distanceConstraint.from(units.build().mapToObj(u -> distance.from(u)).toArray(Distance[]::new));
    }
}
