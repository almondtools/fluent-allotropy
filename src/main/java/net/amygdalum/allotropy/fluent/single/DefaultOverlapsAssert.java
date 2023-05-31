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

public class DefaultOverlapsAssert<T extends VisualElement> implements OverlapsAssert<T> {

    private T subject;
    private List<DirectionalDistanceConstraint> overlapConstraints;
    private Precision precision;

    public DefaultOverlapsAssert(T subject) {
        this.subject = subject;
        this.overlapConstraints = new ArrayList<>();
        this.precision = exact();
    }

    @Override
    public AndAssert<VisualElement> withElement(VisualElement object) {
        VisualOperand s = op(subject);
        VisualOperand o = op(object);
        if (s.inside(o)) {
            throw expected(subject)
                .overlapping(object)
                .butWas("inside")
                .asAssertionError();
        } else if (s.outside(o)) {
            throw expected(subject)
                .overlapping(object)
                .butWas("outside")
                .asAssertionError();
        } else if (s.around(o)) {
            throw expected(subject)
                .overlapping(object)
                .butWas("containing")
                .asAssertionError();
        }
        List<Directed<Distance>> distances = List.of(
            at(N).distance(new PixelDistance(-s.topDistanceToBottom(o))),
            at(E).distance(new PixelDistance(-s.rightDistanceToLeft(o))),
            at(S).distance(new PixelDistance(-s.bottomDistanceToTop(o))),
            at(W).distance(new PixelDistance(-s.leftDistanceToRight(o))));
        List<Directed<Distance>> violatedConstraints = distances.stream()
            .filter(d -> overlapConstraints.stream()
                .filter(c -> c.test(d.direction()))
                .findFirst()
                .map(c -> !c.test(d.subject()))
                .orElse(false))
            .toList();
        if (!violatedConstraints.isEmpty()) {
            throw expected(subject)
                .containing(object)
                .with("distance")
                .__(overlapConstraints.stream()
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
    public OverlapsAssert<T> about(DirectionalDistanceConstraint constraint) {
        this.overlapConstraints.add(constraint.withPrecision(precision));
        return this;
    }

    @Override
    public OverlapsAssert<T> withPrecision(Precision precision) {
        if (overlapConstraints.isEmpty()) {
            this.precision = precision;
        } else {
            int index = overlapConstraints.size() - 1;
            overlapConstraints.set(index, overlapConstraints.get(index).withPrecision(precision));
        }
        return this;
    }

}
