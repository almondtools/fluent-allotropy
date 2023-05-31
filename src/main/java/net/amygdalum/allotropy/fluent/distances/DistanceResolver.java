package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;
import static net.amygdalum.allotropy.fluent.elements.VisualOperand.op;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.DoubleStream.Builder;

import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualOperand;

public record DistanceResolver(Set<CardinalDirection> directions) {

    public static final DistanceResolver DEFAULT = new DistanceResolver(N, E, S, W);

    public DistanceResolver(CardinalDirection ... directions) {
        this(Set.of(directions));
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

}
