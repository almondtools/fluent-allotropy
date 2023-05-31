package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public interface DimensionConstrainingAssert<T extends VisualElement> extends Assert {

    AndAssert<T> pixels();

    AndAssert<T> percentOf(VisualElement element, Dimension dimension);

    AndAssert<T> percentOf(VisualElement element);

    AndAssert<T> percentOf(Dimension dimension);

}
