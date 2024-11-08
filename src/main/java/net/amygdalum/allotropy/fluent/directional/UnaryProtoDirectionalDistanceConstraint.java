package net.amygdalum.allotropy.fluent.directional;

import net.amygdalum.allotropy.fluent.distances.AsPercentOf;
import net.amygdalum.allotropy.fluent.distances.AsPixels;

public class UnaryProtoDirectionalDistanceConstraint<T extends DirectionalDistanceConstrainable<T>> {

    private DirectionalDistanceConstraintBuilder builder;
    private T assertion;

    public UnaryProtoDirectionalDistanceConstraint(DirectionalDistanceConstraintBuilder builder, T assertion) {
        this.builder = builder;
        this.assertion = assertion;
    }

    public DirectionProtoDirectionalDistanceConstraint<T> pixels() {
        builder.addResolver(new AsPixels());
        return new DirectionProtoDirectionalDistanceConstraint<>(builder, assertion);
    }

    public DirectionProtoDirectionalDistanceConstraint<T> percent() {
        builder.addResolver(new AsPercentOf());
        return new DirectionProtoDirectionalDistanceConstraint<>(builder, assertion);
    }

}
