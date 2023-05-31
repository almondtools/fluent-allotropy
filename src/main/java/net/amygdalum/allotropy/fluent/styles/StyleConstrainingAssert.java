package net.amygdalum.allotropy.fluent.styles;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.QuitableStyleAssert;

public interface StyleConstrainingAssert<T extends VisualElement> extends Assert {

    default QuitableStyleAssert<T> is(String value) {
        return satisfies(HasStyleConstraint.is(value));
    }

    default QuitableStyleAssert<T> contains(String part) {
        return satisfies(ContainsStyleConstraint.contains(part));
    }

    StyleConstrainingAssert<T> mappedTo(Function<String, String> mapping);

    QuitableStyleAssert<T> satisfies(StyleConstraint constraint);

}
