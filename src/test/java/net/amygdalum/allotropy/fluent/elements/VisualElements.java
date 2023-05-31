package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.ElementProperty.DISPLAYED;
import static net.amygdalum.allotropy.fluent.elements.ElementProperty.ENABLED;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class VisualElements {

    public static FakeVisualElement visualElement() {
        return new FakeVisualElement();
    }

    public static class FakeVisualElement
        implements VisualElement, StyleableElement, TextContainingElement, DisplayableElement, AsVisualElement<FakeVisualElement> {

        private String text;
        private Bounds bounds;
        private Map<String, String> effectiveStyles;
        private Set<ElementProperty> properties;
        private VisualElement parent;

        public FakeVisualElement() {
            this.effectiveStyles = new LinkedHashMap<>();
            this.properties = EnumSet.of(ENABLED, DISPLAYED);
        }

        @Override
        public FakeVisualElement asVisualElement() {
            return this;
        }

        public FakeVisualElement withParent(VisualElement parent) {
            this.parent = parent;
            return this;
        }

        public FakeVisualElement withText(String text) {
            this.text = text;
            return this;
        }

        public FakeVisualElement withEffectiveStyle(String... keyValues) {
            for (int i = 0; i < keyValues.length; i++) {
                String key = keyValues[i];
                i++;
                String value = keyValues[i];
                this.effectiveStyles.put(key, value);
            }
            return this;
        }

        public FakeVisualElement withProperties(ElementProperty property) {
            properties.add(property);
            return this;
        }

        public FakeVisualElement withoutProperties(ElementProperty property) {
            properties.remove(property);
            return this;
        }

        @Override
        public String text() {
            return text;
        }

        @Override
        public Bounds bounds() {
            return bounds;
        }

        @Override
        public Optional<VisualElement> parent() {
            return Optional.ofNullable(parent);
        }

        @Override
        public String style(String attribute) {
            return effectiveStyles.getOrDefault(attribute, "");
        }

        @Override
        public boolean isDisplayed() {
            return properties.contains(DISPLAYED);
        }

        @Override
        public String toString() {
            return text == null
                ? "[]"
                : "[" + text + "]";
        }
    }
}
