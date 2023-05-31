package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;
import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.elements.TextContainingElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.text.TextConstraint;

public class DefaultTextAssert<T extends VisualElement> implements QuitableTextAssert<T> {

    private T subject;
    private Function<String, String> mapping;

    public DefaultTextAssert(T subject) {
        this.subject = subject;
        this.mapping = Function.identity();
    }

    @Override
    public TextAssert<T> mappedTo(Function<String, String> mapping) {
        this.mapping = this.mapping.andThen(mapping);
        return this;
    }

    @Override
    public QuitableTextAssert<T> satisfies(TextConstraint constraint) {
        if (subject instanceof TextContainingElement textContainer) {
            String text = mapping.apply(textContainer.text());
            if (!constraint.test(text)) {
                throw expected(subject)
                    .toHave("text")
                    .__(constraint.description())
                    .butWas(value(textContainer.text()))
                    .asAssertionError();
            }
            ;
        } else {
            throw new IllegalArgumentException("text asserts are only possible on VisualElements with interface facet TextContainingElement");
        }
        return this;
    }

    @Override
    public VisualElementAssert<T> and() {
        return new DefaultVisualElementAssert<>(subject);
    }

}
