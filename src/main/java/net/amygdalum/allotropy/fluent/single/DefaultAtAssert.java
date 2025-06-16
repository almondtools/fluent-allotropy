package net.amygdalum.allotropy.fluent.single;

import static java.util.stream.Collectors.joining;
import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.elements.AsVisualElement.toVisualElement;
import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.distances.AssertionContext;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.DistanceResolver;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultAtAssert<T extends VisualElement> implements AtAssert<T> {

    private T subject;
    private DistanceConstraint distanceConstraint;
    private DistanceResolver distanceResolver;
    private Precision precision;

    public DefaultAtAssert(T subject) {
        this.subject = subject;
        this.distanceConstraint = DistanceConstraint.NONE;
        this.distanceResolver = DistanceResolver.DEFAULT;
        this.precision = exact();
    }

    @Override
    public AndAssert<T> of(Function<T, ?> selfRelative) {
        var element = selfRelative.apply(subject);
        return ofElement(toVisualElement(element));
    }

    @Override
    public AndAssert<T> ofElement(VisualElement object) {
        Distance dist = distanceResolver.resolveDistance(subject, object)
            .orElseThrow(() -> expected(subject)
                .and(object)
                .toBe(distanceResolver.description())
                .butWere("overlapping or skew")
                .asAssertionError());
        for (Dimension dimension : distanceResolver.dimensions()) {
            AssertionContext ctx = ctx()
                .dimension(dimension)
                .bounds(object.bounds());
            if (!distanceConstraint.inContext(ctx).test(dist)) {
                throw expected(subject)
                    .toHave("distance")
                    .__(distanceConstraint.describeIn(ctx))
                    .to(object)
                    .at(distanceResolver.directions().stream()
                        .map(d -> d.label())
                        .collect(joining(", ")))
                    .butFound(dist.describeIn(ctx))
                    .asAssertionError();
            }
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public AtAssert<T> to(CardinalDirection direction) {
        if (!narrowableTo(direction)) {
            throw new IllegalArgumentException("cannot combine " + distanceResolver.description() + " with direction " + direction.label());
        }
        this.distanceResolver = new DistanceResolver(direction);
        return this;
    }

    private boolean narrowableTo(CardinalDirection direction) {
        return distanceResolver.directions().contains(direction);
    }

    @Override
    public AtAssert<T> about(DistanceConstraint distanceConstraint) {
        this.distanceConstraint = distanceConstraint.withPrecision(precision);
        return this;
    }

    @Override
    public AtAssert<T> withPrecision(Precision precision) {
        if (distanceConstraint == DistanceConstraint.NONE) {
            this.precision = precision;
        } else {
            distanceConstraint = distanceConstraint.withPrecision(precision);
        }
        return this;
    }

}
