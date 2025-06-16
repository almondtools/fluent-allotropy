package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expectElement;
import static net.amygdalum.allotropy.fluent.Expectations.expectElements;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.utils.Arrays.toArray;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.single.VisualElementAssert;

public class DefaultVisualElementsAssert<T extends VisualElement> implements VisualElementsAssert<T> {

    private T[] subjects;

    public DefaultVisualElementsAssert(T[] subjects) {
        this.subjects = subjects;
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
    public <S extends VisualElement> VisualElementsAssert<S> as(Function<T, S> cast) {
        var subjects = Arrays.stream(this.subjects)
            .map(cast)
            .collect(toArray());
        return new DefaultVisualElementsAssert<S>(subjects);
    }

    @Override
    public <S extends VisualElement> AndAssert<T> chunked(Function<T[], S[][]> selector, Consumer<VisualElementsAssert<S>> chunkAssert) {
        S[][] chunked = selector.apply(subjects);
        for (var chunk : chunked) {
            chunkAssert.accept(expectElements(chunk));
        }
        return new DefaultAndAssert<>(subjects);
    }

    @Override
    public VisualElementsAssert<T> sorted(Comparator<T> comparator) {
        T[] sortedSubjects = Arrays.copyOf(subjects, subjects.length);
        Arrays.sort(sortedSubjects, comparator);
        return new DefaultVisualElementsAssert<>(sortedSubjects);
    }

    @Override
    public <S extends VisualElement> AndAssert<T> select(Function<T, S> selector, Consumer<VisualElementsAssert<S>> selectedAssert) {
        S[] selected = Arrays.stream(subjects)
            .map(s -> selector.apply(s))
            .collect(toArray());
        selectedAssert.accept(new DefaultVisualElementsAssert<>(selected));
        return new DefaultAndAssert<>(subjects);
    }

    @Override
    public CountAssert<T> count() {
        return new DefaultCountAssert<>(subjects);
    }
}
