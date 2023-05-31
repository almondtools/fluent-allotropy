package net.amygdalum.allotropy.fluent.multiple;

import static java.util.Arrays.asList;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import net.amygdalum.allotropy.fluent.count.CountConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class DefaultCountAssert<T extends VisualElement> implements CountAssert<T> {

    private T[] subjects;

    public DefaultCountAssert(T[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public AndAssert<T> about(CountConstraint count) {
        int found = subjects.length;
        if (!count.test(found)) {
            throw expected("size")
                .of(asList(subjects))
                .toBe(count.description())
                .butWas(found)
                .asAssertionError();

        }
        return new DefaultAndAssert<>(subjects);
    }

}
