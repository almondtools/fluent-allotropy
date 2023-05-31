package net.amygdalum.allotropy.fluent.directional;

public class BinaryProtoDirectionalDistanceConstraint<T extends DirectionalDistanceConstrainable<T>> {

    private DirectionalDistanceConstraintBuilder builder;
    private T assertion;

    public BinaryProtoDirectionalDistanceConstraint(DirectionalDistanceConstraintBuilder builder, T assertion) {
        this.builder = builder;
        this.assertion = assertion;
    }

    public UnaryProtoDirectionalDistanceConstraint<T> and(double units) {
        builder.addUnits(units);
        return new UnaryProtoDirectionalDistanceConstraint<>(builder, assertion);
    }

}
