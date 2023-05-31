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

public interface NearAssert<T extends VisualElement> extends OfAssert<T>, DistanceConstrainable<NearAssert<T>>, Precisable<NearAssert<T>> {

    default UnaryProtoDistanceConstraint<NearAssert<T>> less(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(LtDistanceConstraint::lt));
    }

    default UnaryProtoDistanceConstraint<NearAssert<T>> greater(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(GtDistanceConstraint::gt));
    }

    default UnaryProtoDistanceConstraint<NearAssert<T>> about(double units) {
        return about(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(EqDistanceConstraint::eq));
    }

    default UnaryProtoDistanceConstraint<NearAssert<T>> about(DistanceConstraintBuilder builder) {
        return new UnaryProtoDistanceConstraint<>(builder, this);
    }

    default BinaryProtoDistanceConstraint<NearAssert<T>> between(double units) {
        return between(new DistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(BetweenDistanceConstraint::between));
    }

    default BinaryProtoDistanceConstraint<NearAssert<T>> between(DistanceConstraintBuilder builder) {
        return new BinaryProtoDistanceConstraint<>(builder, this);
    }

    default ProtoPrecision<NearAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

    default NearAssert<T> top() {
        return to(N);
    }


    default NearAssert<T> right() {
        return to(E);
    }


    default NearAssert<T> bottom() {
        return to(S);
    }


    default NearAssert<T> left() {
        return to(W);
    }


    NearAssert<T> to(CardinalDirection direction);

}
