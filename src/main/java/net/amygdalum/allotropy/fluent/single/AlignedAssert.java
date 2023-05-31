package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.alignment.Alignment.to;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;

import net.amygdalum.allotropy.fluent.alignment.Alignment;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.ProtoPrecision;

public interface AlignedAssert<T extends VisualElement> extends WithAssert<T>, Precisable<AlignedAssert<T>> {

    default AlignedAssert<T> top() {
        return alignedTo(N);
    }

    default AlignedAssert<T> bottom() {
        return alignedTo(S);
    }

    default AlignedAssert<T> left() {
        return alignedTo(W);
    }

    default AlignedAssert<T> right() {
        return alignedTo(E);
    }

    AlignedAssert<T> all();

    AlignedAssert<T> centered();

    default AlignedAssert<T> alignedTo(CardinalDirection direction) {
        return alignedTo(to(direction));
    }

    AlignedAssert<T> alignedTo(Alignment alignment);

    default ProtoPrecision<AlignedAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

}
