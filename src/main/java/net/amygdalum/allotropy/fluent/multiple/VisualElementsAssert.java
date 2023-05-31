package net.amygdalum.allotropy.fluent.multiple;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.VisualElementAssert;

public interface VisualElementsAssert<T extends VisualElement> extends Assert {

    AlignedAssert<T> alignedVertically();

    AlignedAssert<T> alignedHorizontally();

    AndAssert<T> each(Consumer<VisualElementAssert<T>> elementAssert);

    <S extends VisualElement> AndAssert<T> chunked(Function<T[], S[][]> selector, Consumer<VisualElementsAssert<S>> chunkAssert);

    VisualElementsAssert<T> sorted(Comparator<T> comparator);

    <S extends VisualElement> AndAssert<T> select(Function<T, S> selector, Consumer<VisualElementsAssert<S>> selectedAssert);

    CountAssert<T> count();

}
