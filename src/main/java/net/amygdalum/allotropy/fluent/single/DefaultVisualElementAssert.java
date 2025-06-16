package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.function.Consumer;
import java.util.function.Function;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.LayerRelation;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.multiple.DefaultVisualElementsAssert;
import net.amygdalum.allotropy.fluent.multiple.VisualElementsAssert;

public class DefaultVisualElementAssert<T extends VisualElement> implements VisualElementAssert<T> {

    private T subject;

    public DefaultVisualElementAssert(T subject) {
        this.subject = subject;
    }

    public AtAssert<T> at() {
        return new DefaultAtAssert<>(subject);
    }

    @Override
    public InsideAssert<T> inside() {
        return new DefaultInsideAssert<>(subject);
    }

    @Override
    public OverlapsAssert<T> overlaps() {
        return new DefaultOverlapsAssert<>(subject);
    }

    @Override
    public ContainsAssert<T> contains() {
        return new DefaultContainsAssert<>(subject);
    }

    @Override
    public LayerAssert<T> layered(LayerRelation direction) {
        return new DefaultLayerAssert<>(subject, direction);
    }

    @Override
    public DimensionalAssert<T> hasDimension(Dimension dimension) {
        return new DefaultDimensionalAssert<>(subject, dimension);
    }

    @Override
    public AlignedAssert<T> alignedVertically() {
        return new DefaultAlignedAssert<>(subject, VERTICAL);
    }

    @Override
    public AlignedAssert<T> alignedHorizontally() {
        return new DefaultAlignedAssert<>(subject, HORIZONTAL);
    }

    @Override
    public StyleAssert<T> effectiveStyle() {
        return new DefaultStyleAssert<>(subject);
    }

    @Override
    public TextAssert<T> text() {
        return new DefaultTextAssert<>(subject);
    }

    @Override
    public AndAssert<T> property(Constraint<VisualElement> condition) {
        if (!condition.test(subject)) {
            throw expected(subject)
                .toBe(condition.description())
                .butWas("not")
                .asAssertionError();
        }
        return new DefaultAndAssert<>(subject);
    }

    @Override
    public <S extends VisualElement> AndAssert<T> select(Function<T, S> selector, Consumer<VisualElementAssert<S>> selectedAssert) {
        selectedAssert.accept(new DefaultVisualElementAssert<S>(selector.apply(subject)));
        return new DefaultAndAssert<T>(subject);
    }

    @Override
    public <S extends VisualElement> AndAssert<T> spread(Function<T, S[]> selector, Consumer<VisualElementsAssert<S>> selectedAssert) {
        selectedAssert.accept(new DefaultVisualElementsAssert<S>(selector.apply(subject)));
        return new DefaultAndAssert<T>(subject);
    }
}
