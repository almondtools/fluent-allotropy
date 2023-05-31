package net.amygdalum.allotropy.fluent.multiple;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class DefaultAndAssert<T extends VisualElement> implements AndAssert<T> {

    private T[] subjects;

    public DefaultAndAssert(T[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public VisualElementsAssert<T> and() {
        return new DefaultVisualElementsAssert<>(subjects);
    }

}
