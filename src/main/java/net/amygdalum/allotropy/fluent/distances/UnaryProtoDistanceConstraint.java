package net.amygdalum.allotropy.fluent.distances;

public class UnaryProtoDistanceConstraint<T extends DistanceConstrainable<T>> {

    private DistanceConstraintBuilder builder;
    private T assertion;

    public UnaryProtoDistanceConstraint(DistanceConstraintBuilder builder, T assertion) {
        this.builder = builder;
        this.assertion = assertion;
    }

    public T pixels() {
        builder.addResolver(new AsPixels());
        return assertion.about(builder.build());
    }

}
