package net.amygdalum.allotropy.fluent;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.elements.VisualElements.visualElement;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElements;
import net.amygdalum.allotropy.fluent.elements.VisualElements.FakeVisualElement;
import net.amygdalum.allotropy.fluent.elements.WebElements;
import net.amygdalum.allotropy.fluent.elements.WebElements.FakeWebElement;

class ExpectationsTest {

    @Nested
    class testExpect {
        @Test
        void withWebElements() {
            FakeWebElement element = WebElements.webElement();
            expect(List.of(element)).count().equal(1);
        }

        @Test
        void withVisualElements() {
            FakeVisualElement element = VisualElements.visualElement();
            expect(List.of(element)).count().equal(1);
        }

        @Test
        void withAsVisualElements() {
            AsVisualElement<VisualElement> element = new MyElement();
            expect(List.of(element)).count().equal(1);
        }

        @Test
        void withUpCastingElements() {
            AsVisualElement<MyBetterElement> element = new MyBetterElement(visualElement());
            expect(List.of(element))
                .as(s -> (MyBetterElement) s)
                .count().equal(1);
        }

        @Test
        void withFailedUpCastingElements() {
            AsVisualElement<VisualElement> element = new MyElement();
            assertThrows(ClassCastException.class, () -> expect(List.of(element))
                .as(s -> (MyBetterElement) s)
                .count().equal(1));

        }
    }

    private static record MyElement() implements AsVisualElement<VisualElement> {

        @Override
        public VisualElement asVisualElement() {
            return visualElement();
        }

    }

    private static record MyBetterElement(VisualElement wrapped) implements AsVisualElement<MyBetterElement>, VisualElement {

        @Override
        public MyBetterElement asVisualElement() {
            return this;
        }

        @Override
        public Bounds bounds() {
            return wrapped.bounds();
        }

        @Override
        public Optional<VisualElement> parent() {
            return wrapped.parent();
        }

    }
}
