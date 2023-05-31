package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class DefaultAndAssert<T extends VisualElement> implements AndAssert<T> {

    private T subject;

    public DefaultAndAssert(T subject) {
        this.subject = subject;
    }

    @Override
    public VisualElementAssert<T> and() {
        return new DefaultVisualElementAssert<>(subject);
    }

}
