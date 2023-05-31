package net.amygdalum.allotropy.fluent.single;

import static java.util.stream.Collectors.joining;
import static net.amygdalum.allotropy.fluent.directions.Directed.at;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;
import static net.amygdalum.allotropy.fluent.elements.VisualOperand.op;
import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.ArrayList;
import java.util.List;

import net.amygdalum.allotropy.fluent.directional.DirectionalDistanceConstraint;
import net.amygdalum.allotropy.fluent.directions.Directed;
import net.amygdalum.allotropy.fluent.distances.Distance;
import net.amygdalum.allotropy.fluent.distances.PixelDistance;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualOperand;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultInsideAssert<T extends VisualElement> implements InsideAssert<T> {

    private T subject;
    private List<DirectionalDistanceConstraint> insideConstraints;
    private Precision precision;

    public DefaultInsideAssert(T subject) {
        this.subject = subject;
        this.insideConstraints = new ArrayList<>();
        this.precision = exact();
    }

    @Override
    public AndAssert<T> ofElement(VisualElement object) {
        VisualOperand s = op(subject);
        VisualOperand o = op(object);
        if (s.around(o)) {
            throw expected(subject)
                .insideOf(object)
                .butWas("containing")
                .asAssertionError();
        } else if (s.outside(o)) {
            throw expected(subject)
                .insideOf(object)
                .butWas("outside")
                .asAssertionError();
        } else if (s.overlapping(o)) {
            throw expected(subject)
                .insideOf(object)
                .butWas("overlapping")
                .asAssertionError();
        }
        List<Directed<Distance>> distances = List.of(
            at(N).distance(new PixelDistance(s.topDistance(o))),
            at(E).distance(new PixelDistance(s.rightDistance(o))),
            at(S).distance(new PixelDistance(s.bottomDistance(o))),
            at(W).distance(new PixelDistance(s.leftDistance(o))));
        List<Directed<Distance>> violatedConstraints = distances.stream()
            .filter(d -> insideConstraints.stream()
                .filter(c -> c.test(d.direction()))
                .findFirst()
                .map(c -> !c.test(d.subject()))
                .orElse(false))
            .toList();
        if (!violatedConstraints.isEmpty()) {
            throw expected(subject)
                .insideOf(object)
                .with("distance")
                .__(insideConstraints.stream()
                    .map(d -> d.description())
                    .collect(joining(", ")))
                .butWas(violatedConstraints.stream()
                    .map(d -> d.subject() + " " + d.direction().label())
                    .collect(joining(", ")))
                .asAssertionError();
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public InsideAssert<T> about(DirectionalDistanceConstraint constraint) {
        this.insideConstraints.add(constraint.withPrecision(precision));
        return this;
    }

    @Override
    public InsideAssert<T> withPrecision(Precision precision) {
        if (insideConstraints.isEmpty()) {
            this.precision = precision;
        } else {
            int index = insideConstraints.size() - 1;
            insideConstraints.set(index, insideConstraints.get(index).withPrecision(precision));
        }
        return this;
    }

}
