package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.AsVisualElement.toVisualElements;
import static net.amygdalum.allotropy.fluent.elements.VisualElements.visualElement;
import static net.amygdalum.allotropy.fluent.elements.WebElements.webElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AsVisualElementTest {

    @Nested
    class testToVisualElements {
        @Test
        void withWebElement() {
            var element = webElement();
            var visualElement = toVisualElements(element);
            assertThat(visualElement).hasSize(1);
            assertThat(visualElement).singleElement().isInstanceOf(WebVisualElement.class);
        }

        @Test
        void withVisualElement() {
            var element = visualElement();
            var visualElement = toVisualElements(element);
            assertThat(visualElement).hasSize(1);
            assertThat(visualElement).singleElement().isSameAs(element);
        }

        @Test
        void withAsVisualElement() {
            var wrapped = visualElement();
            var element = asVisualElement(wrapped);
            var visualElement = toVisualElements(element);
            assertThat(visualElement).hasSize(1);
            assertThat(visualElement).singleElement().isSameAs(wrapped);
        }

        @Test
        void withMixedArray() {
            var elements = toVisualElements(new Object[] {webElement(), visualElement(), asVisualElement(visualElement())});
            var visualElement = toVisualElements(elements);
            assertThat(visualElement).hasSize(3);
        }

        @Test
        void withMixedList() {
            var elements = toVisualElements(List.of(webElement(), visualElement(), asVisualElement(visualElement())));
            var visualElement = toVisualElements(elements);
            assertThat(visualElement).hasSize(3);
        }

        @Test
        void failing() {
            assertThrows(IllegalArgumentException.class, () -> toVisualElements(new Object()));
            assertThrows(IllegalArgumentException.class, () -> toVisualElements(new Object[] {new Object()}));
            assertThrows(IllegalArgumentException.class, () -> toVisualElements(List.of(new Object())));
        }
    }

    private AsVisualElement<VisualElement> asVisualElement(VisualElement wrapped) {
        return new AsVisualElement<VisualElement>() {

            @Override
            public VisualElement asVisualElement() {
                return wrapped;
            }
        };
    }

}
