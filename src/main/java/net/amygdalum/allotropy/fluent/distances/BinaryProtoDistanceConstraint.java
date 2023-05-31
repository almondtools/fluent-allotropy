package net.amygdalum.allotropy.fluent.distances;

public class BinaryProtoDistanceConstraint<T extends DistanceConstrainable<T>> {

    private DistanceConstraintBuilder builder;
    private T assertion;

    public BinaryProtoDistanceConstraint(DistanceConstraintBuilder builder, T assertion) {
        this.builder = builder;
        this.assertion = assertion;
    }

    public UnaryProtoDistanceConstraint<T> and(double units) {
        builder.addUnits(units);
        return new UnaryProtoDistanceConstraint<>(builder, assertion);
    }

}
