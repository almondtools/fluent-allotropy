package net.amygdalum.allotropy.fluent.single;

import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;

public interface OfAssert<T extends VisualElement> extends Assert {

    default AndAssert<T> of(WebElement object) {
        return ofElement(new WebVisualElement(object));
    }

    default AndAssert<T> of(AsVisualElement<?> object) {
        return ofElement(object.asVisualElement());
    }

    AndAssert<T> ofElement(VisualElement object);
}
