package net.amygdalum.allotropy.fluent.single;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.text.ContainsConstraint;
import net.amygdalum.allotropy.fluent.text.EndsWithConstraint;
import net.amygdalum.allotropy.fluent.text.EqualsConstraint;
import net.amygdalum.allotropy.fluent.text.StartsWithConstraint;
import net.amygdalum.allotropy.fluent.text.TextConstraint;

public interface TextAssert<T extends VisualElement> extends Assert {

    default QuitableTextAssert<T> startsWith(String prefix) {
        return satisfies(StartsWithConstraint.startsWith(prefix));
    }

    default QuitableTextAssert<T> endsWith(String suffix) {
        return satisfies(EndsWithConstraint.endsWith(suffix));
    }

    default QuitableTextAssert<T> contains(String infix) {
        return satisfies(ContainsConstraint.contains(infix));
    }

    default QuitableTextAssert<T> equalTo(String text) {
        return satisfies(EqualsConstraint.equals(text));
    }

    QuitableTextAssert<T> satisfies(TextConstraint constraint);

    TextAssert<T> mappedTo(Function<String, String> mapping);
}
