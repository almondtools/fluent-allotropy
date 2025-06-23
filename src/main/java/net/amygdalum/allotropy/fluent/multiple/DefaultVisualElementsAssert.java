package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expectElement;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.utils.Arrays.toArray;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.AssertContext;
import net.amygdalum.allotropy.fluent.single.VisualElementAssert;

public class DefaultVisualElementsAssert<T extends VisualElement> implements VisualElementsAssert<T> {

    private static AssertContext<VisualElementsAssert<?>> context = new AssertContext<>();

    private T[] subjects;

    public DefaultVisualElementsAssert(T[] subjects) {
        this.subjects = subjects;
        context.push(this);
    }

    public static <T extends VisualElement> VisualElementsAssert<T> find(T[] subjects) {
        VisualElementsAssert<?> item = context.peek();
        if (item.hasSubjects(subjects)) {
            @SuppressWarnings("unchecked")
            VisualElementsAssert<T> foundItem = (VisualElementsAssert<T>) item;
            return foundItem;
        }
        context.remove();
        return find(subjects);
    }

    @Override
    public <S extends VisualElement> boolean hasSubjects(S[] subjects) {
        return this.subjects == subjects;
    }

    @Override
    public <S extends VisualElement> VisualElementsAssert<S> backAs(Class<S> clazz) {
        VisualElementsAssert<?> removed = context.remove();
        return removed.as(clazz);
    }

    @Override
    public AlignedAssert<T> alignedVertically() {
        return new DefaultAlignedAssert<>(subjects, VERTICAL);
    }

    @Override
    public AlignedAssert<T> alignedHorizontally() {
        return new DefaultAlignedAssert<>(subjects, HORIZONTAL);
    }

    @Override
    public AndAssert<T> property(Constraint<VisualElement> condition) {
        for (var subject : subjects) {
            if (!condition.test(subject)) {
                throw expected(subject)
                    .toBe(condition.description())
                    .butWas("not")
                    .asAssertionError();
            }
        }
        return new DefaultAndAssert<>(subjects);
    }

    @Override
    public AndAssert<T> each(Consumer<VisualElementAssert<T>> elementAssert) {
        for (var subject : subjects) {
            elementAssert.accept(expectElement(subject));
        }
        return new DefaultAndAssert<>(subjects);
    }

    @Override
    public <S extends VisualElement> VisualElementsAssert<S> as(Class<S> clazz) {
        var subjects = Arrays.stream(this.subjects)
            .map(clazz::cast)
            .collect(toArray());
        return new DefaultVisualElementsAssert<S>(subjects);
    }

    @Override
    public <S extends VisualElement> VisualElementsAssert<S> selecting(Selecting<T, S> selector) {
        S[] selectedSubjects = selector.apply(subjects);
        return new DefaultVisualElementsAssert<S>(selectedSubjects);
    }

    @Override
    public VisualElementsAssert<T> sorted(Comparator<T> comparator) {
        T[] sortedSubjects = Arrays.copyOf(subjects, subjects.length);
        Arrays.sort(sortedSubjects, comparator);
        return new DefaultVisualElementsAssert<>(sortedSubjects);
    }

    @Override
    public CountAssert<T> count() {
        return new DefaultCountAssert<>(subjects);
    }

}
