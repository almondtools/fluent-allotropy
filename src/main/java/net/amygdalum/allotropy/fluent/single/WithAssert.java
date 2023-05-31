package net.amygdalum.allotropy.fluent.single;

import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;

public interface WithAssert<T extends VisualElement> extends Assert {

    default AndAssert<T> with(WebElement object) {
        return withElement(new WebVisualElement(object));
    }

    default AndAssert<T> with(AsVisualElement<?> object) {
        return withElement(object.asVisualElement());
    }

    AndAssert<T> withElement(VisualElement object);
}
