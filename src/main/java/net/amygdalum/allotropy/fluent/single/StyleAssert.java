package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.styles.StyleConstrainingAssert;

public interface StyleAssert<T extends VisualElement> extends Assert {

    StyleConstrainingAssert<T> attribute(String key);

}
