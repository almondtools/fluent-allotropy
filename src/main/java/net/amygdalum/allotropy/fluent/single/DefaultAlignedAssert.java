package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.alignment.Alignment.to;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;
import static net.amygdalum.allotropy.fluent.elements.VisualOperand.op;
import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;
import static net.amygdalum.allotropy.fluent.utils.Exceptions.defaultInExhaustiveMatch;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import net.amygdalum.allotropy.fluent.alignment.Alignment;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.FalseWithExplanation;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualOperand;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultAlignedAssert<T extends VisualElement> implements AlignedAssert<T> {

    private T subject;
    private Dimension dimension;
    private Set<Alignment> alignments;
    private Precision precision;

    public DefaultAlignedAssert(T subject, Dimension dimension) {
        this.subject = subject;
        this.dimension = dimension;
        this.alignments = new HashSet<>();
        this.precision = exact();
    }

    @Override
    public AndAssert<T> withElement(VisualElement object) {
        VisualOperand s = op(subject).withPrecision(precision);
        VisualOperand o = op(object).withPrecision(precision);
        if (dimension == HORIZONTAL) {
            if (s.nextTo(o) instanceof FalseWithExplanation e) {
                throw expected(subject)
                    .aligned(dimension.adLabel())
                    .with(object)
                    .butWas(e.explanation())
                    .asAssertionError();
            }
        } else if (dimension == VERTICAL) {
            if (s.stacked(o) instanceof FalseWithExplanation e) {
                throw expected(subject)
                    .aligned(dimension.adLabel())
                    .with(object)
                    .butWas(e.explanation())
                    .asAssertionError();
            }
        } else {
            throw defaultInExhaustiveMatch();
        }
        for (Alignment alignment : alignments) {
            double scoord = s.at(alignment);
            double ocoord = o.at(alignment);
            if (!precision.eq(scoord, ocoord)) {
                throw expected(subject)
                    .aligned(dimension.adLabel())
                    .__(alignment.label())
                    .with(object)
                    .butNot(alignment.label())
                    .asAssertionError();
            }
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public AlignedAssert<T> withPrecision(Precision precision) {
        this.precision = precision;
        return this;
    }

    @Override
    public AlignedAssert<T> all() {
        Stream.of(N, S, W, E)
            .filter(direction -> matches(to(direction)))
            .forEach(this::alignedTo);
        return this;
    }

    @Override
    public AlignedAssert<T> centered() {
        Stream.of(HORIZONTAL, VERTICAL)
            .map(dimension -> Alignment.centered(dimension))
            .filter(centered -> matches(centered))
            .forEach(this::alignedTo);
        return this;
    }

    @Override
    public AlignedAssert<T> alignedTo(Alignment alignment) {
        if (!matches(alignment)) {
            throw new IllegalArgumentException("cannot align " + dimension.adLabel() + " " + alignment.label());
        }
        this.alignments.add(alignment);
        return this;
    }

    private boolean matches(Alignment alignment) {
        return alignment.dimension() != dimension;
    }

}
