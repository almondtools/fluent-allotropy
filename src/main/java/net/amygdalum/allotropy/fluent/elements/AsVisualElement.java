package net.amygdalum.allotropy.fluent.elements;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.WebElement;

public interface AsVisualElement<T extends VisualElement> {

    T asVisualElement();

    static VisualElement[] toVisualElements(Object s) {
        if (s instanceof Object[] a) {
            return Arrays.stream(a)
                .map(AsVisualElement::toVisualElement)
                .toArray(VisualElement[]::new);
        } else if (s instanceof Collection<?> c) {
            return c.stream()
                .map(AsVisualElement::toVisualElement)
                .toArray(VisualElement[]::new);
        } else if (s instanceof WebElement e) {
            return new VisualElement[] {new WebVisualElement(e)};
        } else if (s instanceof VisualElement e) {
            return new VisualElement[] {e};
        } else if (s instanceof AsVisualElement<?> e) {
            return new VisualElement[] {e.asVisualElement()};
        } else {
            throw new IllegalArgumentException("expected list of web elements or visual elements, but got " + s.getClass().getSimpleName());
        }
    }

    static VisualElement toVisualElement(Object s) {
        if (s instanceof WebElement e) {
            return new WebVisualElement(e);
        } else if (s instanceof VisualElement e) {
            return e;
        } else if (s instanceof AsVisualElement<?> e) {
            return e.asVisualElement();
        } else {
            throw new IllegalArgumentException("expected elements or visual elements, but got " + s.getClass().getSimpleName());
        }
    }
}
