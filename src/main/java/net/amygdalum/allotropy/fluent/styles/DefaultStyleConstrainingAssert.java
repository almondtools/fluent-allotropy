package net.amygdalum.allotropy.fluent.styles;

import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;
import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.elements.StyleableElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.DefaultStyleAssert;
import net.amygdalum.allotropy.fluent.single.QuitableStyleAssert;

public class DefaultStyleConstrainingAssert<T extends VisualElement> implements StyleConstrainingAssert<T> {
    private T subject;
    private String attribute;
    private Function<String, String> mapping;

    public DefaultStyleConstrainingAssert(T subject, String attribute) {
        this.subject = subject;
        this.attribute = attribute;
        this.mapping = Function.identity();
    }

    @Override
    public StyleConstrainingAssert<T> mappedTo(Function<String, String> mapping) {
        this.mapping = this.mapping.andThen(mapping);
        return this;
    }

    @Override
    public QuitableStyleAssert<T> satisfies(StyleConstraint constraint) {
        if (subject instanceof StyleableElement styled) {
            String foundValue = mapping.apply(styled.style(attribute));
            if (!constraint.test(foundValue)) {
                throw expected(subject)
                    .toHave(attribute)
                    .__(constraint.description())
                    .butFound(value(foundValue))
                    .asAssertionError();
            }
        } else {
            throw new IllegalArgumentException("style asserts are only possible on VisualElements with interface facet StyleableElement");
        }
        return new DefaultStyleAssert<>(subject);
    }

}
