package net.amygdalum.allotropy.fluent.directional;

import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;

import net.amygdalum.allotropy.fluent.directions.CardinalDirection;

public class DirectionProtoDirectionalDistanceConstraint<T extends DirectionalDistanceConstrainable<T>> {

    private DirectionalDistanceConstraintBuilder builder;
    private T assertion;

    public DirectionProtoDirectionalDistanceConstraint(DirectionalDistanceConstraintBuilder builder, T assertion) {
        this.builder = builder;
        this.assertion = assertion;
    }

    public T top() {
        return to(N);
    }

    public T right() {
        return to(E);
    }

    public T bottom() {
        return to(S);
    }

    public T left() {
        return to(W);
    }


    public T to(CardinalDirection direction) {
        builder.setDirection(direction);
        return assertion.about(builder.build());
    }

}
