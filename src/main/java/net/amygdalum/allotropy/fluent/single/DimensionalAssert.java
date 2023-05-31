package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.distances.EqDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.ProtoPrecision;

public interface DimensionalAssert<T extends VisualElement> extends Precisable<DimensionalAssert<T>>, Assert {

    default DimensionConstrainingAssert<T> equal(double units) {
        return about(new DistanceConstraintBuilder()
            .addAccumulator(EqDistanceConstraint::eq)
            .addUnits(units));
    }

    default DimensionConstrainingAssert<T> greater(double units) {
        return about(new DistanceConstraintBuilder()
            .addAccumulator(GtDistanceConstraint::gt)
            .addUnits(units));
    }

    default DimensionConstrainingAssert<T> less(double units) {
        return about(new DistanceConstraintBuilder()
            .addAccumulator(LtDistanceConstraint::lt)
            .addUnits(units));
    }

    DimensionConstrainingAssert<T> about(DistanceConstraintBuilder builder);

    default ProtoPrecision<DimensionalAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

}
