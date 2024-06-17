package net.amygdalum.allotropy.fluent.multiple;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.conditions.PresenceConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.VisualElementAssert;

public interface VisualElementsAssert<T extends VisualElement> extends Assert {

    AlignedAssert<T> alignedVertically();

    AlignedAssert<T> alignedHorizontally();

    default AndAssert<T> arePresent() {
        return property(PresenceConstraint.isPresent());
    }

    default AndAssert<T> areAbsent() {
        return property(PresenceConstraint.isAbsent());
    }

    AndAssert<T> property(Constraint<VisualElement> condition);

    AndAssert<T> each(Consumer<VisualElementAssert<T>> elementAssert);

    <S extends VisualElement> AndAssert<T> chunked(Function<T[], S[][]> selector, Consumer<VisualElementsAssert<S>> chunkAssert);

    VisualElementsAssert<T> sorted(Comparator<T> comparator);

    <S extends VisualElement> AndAssert<T> select(Function<T, S> selector, Consumer<VisualElementsAssert<S>> selectedAssert);

    CountAssert<T> count();

}
