package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.directions.LayerRelation.BEHIND;
import static net.amygdalum.allotropy.fluent.directions.LayerRelation.INFRONT;

import java.util.function.Consumer;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.conditions.PresenceConstraint;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.LayerRelation;
import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.multiple.VisualElementsAssert;

public interface VisualElementAssert<T extends VisualElement> extends Assert {

    default NearAssert<T> above() {
        return near()
            .top();
    }

    default NearAssert<T> below() {
        return near()
            .bottom();
    }

    default NearAssert<T> left() {
        return near()
            .left();
    }

    default NearAssert<T> right() {
        return near()
            .right();
    }

    NearAssert<T> near();

    InsideAssert<T> inside();

    OverlapsAssert<T> overlaps();

    default AndAssert<T> contains(AsVisualElement<?>... items) {
        return contains().items(items);
    }

    ContainsAssert<T> contains();

    default LayerAssert<T> inFront() {
        return layered(INFRONT);
    }

    default LayerAssert<T> behind() {
        return layered(BEHIND);
    }

    LayerAssert<T> layered(LayerRelation direction);

    default DimensionalAssert<T> width() {
        return hasDimension(HORIZONTAL);
    }

    default DimensionalAssert<T> height() {
        return hasDimension(VERTICAL);
    }

    DimensionalAssert<T> hasDimension(Dimension dimension);

    AlignedAssert<T> alignedVertically();

    AlignedAssert<T> alignedHorizontally();

    TextAssert<T> text();

    StyleAssert<T> effectiveStyle();

    default AndAssert<T> isPresent() {
        return property(PresenceConstraint.isPresent());
    }

    default AndAssert<T> isAbsent() {
        return property(PresenceConstraint.isAbsent());
    }

    AndAssert<T> property(Constraint<VisualElement> condition);

    <S extends VisualElement> AndAssert<T> select(Function<T, S> selector, Consumer<VisualElementAssert<S>> selectedAssert);

    <S extends VisualElement> AndAssert<T> spread(Function<T, S[]> selector, Consumer<VisualElementsAssert<S>> selectedAssert);
}
