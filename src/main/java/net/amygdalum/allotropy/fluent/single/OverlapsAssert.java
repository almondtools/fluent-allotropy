package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.directional.BinaryProtoDirectionalDistanceConstraint;
import net.amygdalum.allotropy.fluent.directional.DirectionalDistanceConstrainable;
import net.amygdalum.allotropy.fluent.directional.DirectionalDistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.directional.UnaryProtoDirectionalDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.BetweenDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.EqDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.ProtoPrecision;

public interface OverlapsAssert<T extends VisualElement> extends Precisable<OverlapsAssert<T>>, DirectionalDistanceConstrainable<OverlapsAssert<T>>, WithAssert<VisualElement>, Assert {

    default UnaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> less(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(LtDistanceConstraint::lt));
    }

    default UnaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> greater(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(GtDistanceConstraint::gt));
    }

    default UnaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> about(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(EqDistanceConstraint::eq));
    }

    default UnaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> about(DirectionalDistanceConstraintBuilder builder) {
        return new UnaryProtoDirectionalDistanceConstraint<>(builder, this);
    }

    default BinaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> between(double units) {
        return between(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(BetweenDistanceConstraint::between));
    }

    default BinaryProtoDirectionalDistanceConstraint<OverlapsAssert<T>> between(DirectionalDistanceConstraintBuilder builder) {
        return new BinaryProtoDirectionalDistanceConstraint<>(builder, this);
    }

    default ProtoPrecision<OverlapsAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

}
