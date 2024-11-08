package net.amygdalum.allotropy.fluent.distances;

import static java.util.stream.Collectors.joining;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;
import static net.amygdalum.allotropy.fluent.elements.VisualOperand.op;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.DoubleStream.Builder;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualOperand;

public record DistanceResolver(Set<CardinalDirection> directions) {

    public static final DistanceResolver DEFAULT = new DistanceResolver(N, E, S, W);

    public DistanceResolver(CardinalDirection... directions) {
        this(Set.of(directions));
    }

    public List<Dimension> dimensions() {
        List<Dimension> dimensions = new ArrayList<>(2);
        if (directions.contains(N) || directions.contains(S)) {
            dimensions.add(VERTICAL);
        }
        if (directions.contains(E) || directions.contains(W)) {
            dimensions.add(HORIZONTAL);
        }
        return dimensions;
    }

    public Optional<Distance> resolveDistance(VisualElement subject, VisualElement object) {
        VisualOperand s = op(subject);
        VisualOperand o = op(object);

        Builder builder = DoubleStream.builder();
        if (directions.contains(N)) {
            builder.accept(s.bottomDistanceToTop(o));
        }
        if (directions.contains(E)) {
            builder.accept(s.leftDistanceToRight(o));
        }
        if (directions.contains(S)) {
            builder.accept(s.topDistanceToBottom(o));
        }
        if (directions.contains(W)) {
            builder.accept(s.rightDistanceToLeft(o));
        }
        OptionalDouble minDistance = builder.build()
            .filter(distance -> distance >= 0)
            .min();
        if (minDistance.isPresent()) {
            return Optional.of(new PixelDistance(minDistance.orElse(0)));
        } else {
            return Optional.empty();
        }
    }

    public String description() {
        if (directions.size() == CardinalDirection.values().length) {
            return "near";
        }
        return "at " + directions.stream()
            .map(d -> d.label())
            .collect(joining(" or "));
    }

}
