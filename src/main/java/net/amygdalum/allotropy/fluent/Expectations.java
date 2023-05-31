package net.amygdalum.allotropy.fluent;

import static net.amygdalum.allotropy.fluent.utils.Arrays.toArray;

import java.util.Arrays;

import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;
import net.amygdalum.allotropy.fluent.multiple.DefaultVisualElementsAssert;
import net.amygdalum.allotropy.fluent.multiple.VisualElementsAssert;
import net.amygdalum.allotropy.fluent.single.DefaultVisualElementAssert;
import net.amygdalum.allotropy.fluent.single.VisualElementAssert;

public class Expectations {

    private Expectations() {
    }

    public static VisualElementAssert<WebVisualElement> expect(WebElement subject) {
        return expectElement(new WebVisualElement(subject));
    }

    public static <T extends VisualElement> VisualElementAssert<T> expect(AsVisualElement<T> subject) {
        return expectElement(subject.asVisualElement());
    }

    public static <T extends VisualElement> VisualElementAssert<T> expectElement(T subject) {
        return new DefaultVisualElementAssert<>(subject);
    }

    public static VisualElementsAssert<WebVisualElement> expect(WebElement... subjects) {
        return expectElements(Arrays.stream(subjects)
            .map(s -> new WebVisualElement(s))
            .toArray(WebVisualElement[]::new));
    }

    @SafeVarargs
    public static <T extends VisualElement> VisualElementsAssert<T> expect(AsVisualElement<T>... subjects) {
        return expectElements(Arrays.stream(subjects)
            .map(s -> s.asVisualElement())
            .collect(toArray()));
    }

    @SafeVarargs
    public static <T extends VisualElement> VisualElementsAssert<T> expectElements(T... subjects) {
        return new DefaultVisualElementsAssert<>(subjects);
    }

}
