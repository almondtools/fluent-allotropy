package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultDimensionalAssert<T extends VisualElement> implements DimensionalAssert<T> {

    private T subject;
    private Dimension dimension;
    private Precision precision;

    public DefaultDimensionalAssert(T subject, Dimension dimension) {
        this.subject = subject;
        this.dimension = dimension;
        this.precision = exact();
    }

    @Override
    public DimensionalAssert<T> withPrecision(Precision precision) {
        this.precision = precision;
        return this;
    }

    @Override
    public DimensionConstrainingAssert<T> about(DistanceConstraintBuilder builder) {
        return new DefaultDimensionConstrainingAssert<>(subject, dimension, precision, builder);
    }

}
