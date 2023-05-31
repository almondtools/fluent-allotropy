package net.amygdalum.allotropy.fluent.single;

import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.styles.DefaultStyleConstrainingAssert;
import net.amygdalum.allotropy.fluent.styles.StyleConstrainingAssert;

public class DefaultStyleAssert<T extends VisualElement> implements QuitableStyleAssert<T> {

    private T subject;

    public DefaultStyleAssert(T subject) {
        this.subject = subject;
    }

    @Override
    public StyleConstrainingAssert<T> attribute(String key) {
        return new DefaultStyleConstrainingAssert<>(subject, key);
    }

    @Override
    public VisualElementAssert<T> and() {
        return new DefaultVisualElementAssert<>(subject);
    }

}
