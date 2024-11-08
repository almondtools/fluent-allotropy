package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.distances.AsPercentOf;
import net.amygdalum.allotropy.fluent.distances.AsPixels;
import net.amygdalum.allotropy.fluent.distances.AssertionContext;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.distances.PixelDistance;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultDimensionConstrainingAssert<T extends VisualElement> implements DimensionConstrainingAssert<T> {

    private T subject;
    private Dimension dimension;
    private Precision precision;
    private DistanceConstraintBuilder builder;

    public DefaultDimensionConstrainingAssert(T subject, Dimension dimension, Precision precision, DistanceConstraintBuilder builder) {
        this.subject = subject;
        this.dimension = dimension;
        this.precision = precision;
        this.builder = builder;
    }

    @Override
    public AndAssert<T> pixels() {
        Distance dist = distance();
        DistanceConstraint distanceConstraint = builder.addResolver(new AsPixels()).build();
        check(distanceConstraint, dist, ctx());
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public AndAssert<T> percentOf(VisualElement element, Dimension dimension) {
        Distance dist = distance();
        DistanceConstraint distanceConstraint = builder.addResolver(new AsPercentOf()).build();

        check(distanceConstraint, dist, ctx()
            .dimension(dimension)
            .bounds(element.bounds()));
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public AndAssert<T> percentOf(VisualElement element) {
        return percentOf(element, dimension);
    }

    @Override
    public AndAssert<T> percentOf(Dimension dimension) {
        return percentOf(subject, dimension);
    }

    private Distance distance() {
        return new PixelDistance(subject.bounds().size(dimension));
    }

    private void check(DistanceConstraint distanceConstraint, Distance dist, AssertionContext context) {
        if (!distanceConstraint.withPrecision(precision).inContext(context).test(dist)) {
            throw expected(subject)
                .toHave(dimension.dimensionLabel())
                .__(distanceConstraint.describeIn(context))
                .butFound(dist.describeIn(context))
                .asAssertionError();
        }
    }

}
