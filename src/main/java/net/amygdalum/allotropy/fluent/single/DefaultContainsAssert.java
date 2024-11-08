package net.amygdalum.allotropy.fluent.single;

import static java.util.stream.Collectors.joining;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;
import static net.amygdalum.allotropy.fluent.directions.Directed.at;
import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
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

public class DefaultContainsAssert<T extends VisualElement> implements ContainsAssert<T> {

    private T subject;
    private List<DirectionalDistanceConstraint> insideConstraints;
    private Precision precision;

    public DefaultContainsAssert(T subject) {
        this.subject = subject;
        this.insideConstraints = new ArrayList<>();
        this.precision = exact();
    }

    @Override
    public AndAssert<T> itemElements(VisualElement... items) {
        VisualOperand s = op(subject);
        for (var object : items) {
            VisualOperand o = op(object);
            if (s.inside(o)) {
                throw expected(subject)
                    .containing(object)
                    .butWas("contained")
                    .asAssertionError();
            } else if (s.overlapping(o)) {
                throw expected(subject)
                    .containing(object)
                    .butWas("overlapping")
                    .asAssertionError();
            } else if (s.outside(o)) {
                throw expected(subject)
                    .containing(object)
                    .butWas("outside")
                    .asAssertionError();
            }
            List<Directed<Distance>> distances = List.of(
                at(N).distance(new PixelDistance(o.topDistance(s))),
                at(E).distance(new PixelDistance(o.rightDistance(s))),
                at(S).distance(new PixelDistance(o.bottomDistance(s))),
                at(W).distance(new PixelDistance(o.leftDistance(s))));
            List<Directed<Distance>> violatedConstraints = distances.stream()
                .filter(d -> insideConstraints.stream()
                    .map(c -> c.inContext(ctx()
                        .dimension(d.dimension())
                        .bounds(s.bounds())))
                    .filter(c -> c.test(d.direction()))
                    .findFirst()
                    .map(c -> !c.test(d.subject()))
                    .orElse(false))
                .toList();
            if (!violatedConstraints.isEmpty()) {
                throw expected(subject)
                    .containing(object)
                    .with("distance")
                    .__(insideConstraints.stream()
                        .map(d -> d.describeIn(ctx()
                            .direction(d.direction())
                            .bounds(s.bounds())))
                        .collect(joining(", ")))
                    .butWas(violatedConstraints.stream()
                        .map(d -> d.subject()
                            .describeIn(ctx()
                                .dimension(d.dimension())
                                .bounds(s.bounds()))
                            + " " + d.direction().label())
                        .collect(joining(", ")))
                    .asAssertionError();
            }
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public ContainsAssert<T> about(DirectionalDistanceConstraint constraint) {
        this.insideConstraints.add(constraint.withPrecision(precision));
        return this;
    }

    @Override
    public ContainsAssert<T> withPrecision(Precision precision) {
        if (insideConstraints.isEmpty()) {
            this.precision = precision;
        } else {
            int index = insideConstraints.size() - 1;
            insideConstraints.set(index, insideConstraints.get(index).withPrecision(precision));
        }
        return this;
    }

}
