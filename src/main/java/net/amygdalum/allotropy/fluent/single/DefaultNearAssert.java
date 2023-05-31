package net.amygdalum.allotropy.fluent.single;

import static java.util.stream.Collectors.joining;
import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.DistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.DistanceResolver;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultNearAssert<T extends VisualElement> implements NearAssert<T> {

    private T subject;
    private DistanceConstraint distanceConstraint;
    private DistanceResolver distanceResolver;
    private Precision precision;

    public DefaultNearAssert(T subject) {
        this.subject = subject;
        this.distanceConstraint = DistanceConstraint.NONE;
        this.distanceResolver = DistanceResolver.DEFAULT;
        this.precision = exact();
    }

    @Override
    public AndAssert<T> ofElement(VisualElement object) {
        Distance dist = distanceResolver.resolveDistance(subject, object)
            .orElseThrow(() -> expected(subject)
                .and(object)
                .toBe("near")
                .butWere("overlapping or skew")
                .asAssertionError());
        if (!distanceConstraint.test(dist)) {
            throw expected(subject)
                .toHave("distance")
                .__(distanceConstraint.description())
                .to(object)
                .at(distanceResolver.directions().stream()
                    .map(d -> d.label())
                    .collect(joining(", ")))
                .butFound(dist)
                .asAssertionError();
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public NearAssert<T> to(CardinalDirection direction) {
        this.distanceResolver = new DistanceResolver(direction);
        return this;
    }

    @Override
    public NearAssert<T> about(DistanceConstraint distanceConstraint) {
        this.distanceConstraint = distanceConstraint.withPrecision(precision);
        return this;
    }

    @Override
    public NearAssert<T> withPrecision(Precision precision) {
        if (distanceConstraint == DistanceConstraint.NONE) {
            this.precision = precision;
        } else {
            distanceConstraint = distanceConstraint.withPrecision(precision);
        }
        return this;
    }

}
