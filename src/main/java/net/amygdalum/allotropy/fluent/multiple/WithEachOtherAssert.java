package net.amygdalum.allotropy.fluent.multiple;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public interface WithEachOtherAssert<T extends VisualElement> extends Assert {

    AndAssert<T> withEachOther();

}
