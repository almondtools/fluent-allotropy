package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;

import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.distances.BetweenDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.BinaryProtoDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.DistanceConstrainable;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.distances.EqDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.UnaryProtoDistanceConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.ProtoPrecision;

public interface AtAssert<T extends VisualElement> extends OfAssert<T>, DistanceConstrainable<AtAssert<T>>, Precisable<AtAssert<T>> {

    default UnaryProtoDistanceConstraint<AtAssert<T>> less(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(LtDistanceConstraint::lt));
    }

    default UnaryProtoDistanceConstraint<AtAssert<T>> greater(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(GtDistanceConstraint::gt));
    }

    default UnaryProtoDistanceConstraint<AtAssert<T>> about(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(EqDistanceConstraint::eq));
    }

    default UnaryProtoDistanceConstraint<AtAssert<T>> about(DistanceConstraintBuilder builder) {
        return new UnaryProtoDistanceConstraint<>(builder, this);
    }

    default BinaryProtoDistanceConstraint<AtAssert<T>> between(double units) {
        return between(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(BetweenDistanceConstraint::between));
    }

    default BinaryProtoDistanceConstraint<AtAssert<T>> between(DistanceConstraintBuilder builder) {
        return new BinaryProtoDistanceConstraint<>(builder, this);
    }

    default ProtoPrecision<AtAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

    default AtAssert<T> top() {
        return to(N);
    }

    default AtAssert<T> right() {
        return to(E);
    }

    default AtAssert<T> bottom() {
        return to(S);
    }

    default AtAssert<T> left() {
        return to(W);
    }

    AtAssert<T> to(CardinalDirection direction);

}
