package net.amygdalum.allotropy.fluent.multiple;

import static java.util.stream.Collectors.toSet;
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
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expectedAllOf;
import static net.amygdalum.allotropy.fluent.utils.Exceptions.defaultInExhaustiveMatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import net.amygdalum.allotropy.fluent.alignment.Alignment;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.distances.PixelDistance;
import net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.FalseWithExplanation;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualOperand;
import net.amygdalum.allotropy.fluent.precision.Precision;

public class DefaultAlignedAssert<T extends VisualElement> implements AlignedAssert<T> {

    private T[] subjects;
    private Dimension dimension;
    private Set<Alignment> alignments;
    private boolean equalDistanced;
    private Precision precision;

    public DefaultAlignedAssert(T[] subjects, Dimension dimension) {
        this.subjects = subjects;
        this.dimension = dimension;
        this.alignments = new HashSet<>();
        this.equalDistanced = false;
        this.precision = exact();
    }

    @Override
    public AndAssert<T> withEachOther() {
        for (var combination : combinations()) {
            VisualOperand subject = op(combination.subject).withPrecision(precision);
            VisualOperand object = op(combination.object).withPrecision(precision);

            if (dimension == HORIZONTAL) {
                if (subject.nextTo(object) instanceof FalseWithExplanation e) {
                    throw expected(combination.subject)
                        .aligned(dimension.adLabel())
                        .with(combination.object)
                        .butWas(e.explanation())
                        .asAssertionError();
                }
            } else if (dimension == VERTICAL) {
                if (subject.stacked(object) instanceof FalseWithExplanation e) {
                    throw expected(combination.subject)
                        .aligned(dimension.adLabel())
                        .with(combination.object)
                        .butWas(e.explanation())
                        .asAssertionError();
                }
            } else {
                throw defaultInExhaustiveMatch();
            }
            for (Alignment alignment : alignments) {
                double scoord = subject.at(alignment);
                double ocoord = object.at(alignment);
                if (!precision.eq(scoord, ocoord)) {
                    throw expected(combination.subject)
                        .aligned(dimension.adLabel())
                        .__(alignment.label())
                        .with(combination.object)
                        .butNot(alignment.label())
                        .asAssertionError();
                }
            }
        }
        if (equalDistanced) {
            BiFunction<VisualOperand, VisualOperand, Double> distance = dimension == VERTICAL
                ? (s, o) -> s.bottomDistanceToTop(o)
                : (s, o) -> s.rightDistanceToLeft(o);
            Set<Double> distances = neighbours().stream()
                .map(n -> distance.apply(op(n.subject), op(n.object)))
                .collect(toSet());
            double min = distances.stream().min(Double::compare).orElse(0.0);
            double max = distances.stream().max(Double::compare).orElse(0.0);
            if (!precision.eq(max, min)) {
                throw expectedAllOf(subjects)
                    .aligned(dimension.adLabel())
                    .with("each other")
                    .__("equally distanced")
                    .butFound("between").__(new PixelDistance(min)).and(new PixelDistance(max))
                    .asAssertionError();
            }
        }
        return new DefaultAndAssert<>(subjects);
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

    @Override
    public AlignedAssert<T> equallyDistanced() {
        this.equalDistanced = true;
        return this;
    }

    private boolean matches(Alignment alignment) {
        return alignment.dimension() != dimension;
    }

    private List<Combination<T>> combinations() {
        List<Combination<T>> combinations = new ArrayList<>();
        for (int i = 0; i < subjects.length; i++) {
            for (int j = i + 1; j < subjects.length; j++) {
                combinations.add(new Combination<>(subjects[i], subjects[j]));
            }
        }
        return combinations;
    }

    private List<Combination<T>> neighbours() {
        List<Combination<T>> combinations = new ArrayList<>();
        for (int i = 0; i < subjects.length - 1; i++) {
            combinations.add(new Combination<>(subjects[i], subjects[i + 1]));
        }
        return combinations;
    }

    private record Combination<T>(T subject, T object) {

    }
}
