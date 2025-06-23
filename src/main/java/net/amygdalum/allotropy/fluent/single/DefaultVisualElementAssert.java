package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.LayerRelation;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElementAggregate;
import net.amygdalum.allotropy.fluent.multiple.DefaultVisualElementsAssert;
import net.amygdalum.allotropy.fluent.multiple.VisualElementsAssert;

public class DefaultVisualElementAssert<T extends VisualElement> implements VisualElementAssert<T> {

    private static AssertContext<VisualElementAssert<?>> context = new AssertContext<>();

    private T subject;

    public DefaultVisualElementAssert(T subject) {
        this.subject = subject;
        context.push(this);
    }
    
    public static <T extends VisualElement> VisualElementAssert<T> find(T subject) {
        VisualElementAssert<?> item = context.peek();
        if (item.hasSubject(subject)) {
            @SuppressWarnings("unchecked")
            VisualElementAssert<T> foundItem = (VisualElementAssert<T>) item;
            return foundItem;
        }
        context.remove();
        return find(subject);
    }

    @Override
    public <S extends VisualElement> boolean hasSubject(S subject) {
        return this.subject == subject;
    }

    @Override
    public <S extends VisualElement> VisualElementAssert<S> backAs(Class<S> clazz) {
        VisualElementAssert<?> removed = context.remove();
        return removed.as(clazz);
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
    public <S extends VisualElement> VisualElementAssert<S> as(Class<S> clazz) {
        var subject = clazz.cast(this.subject);
        return new DefaultVisualElementAssert<S>(subject);
    }

    @Override
    public <S extends VisualElement> VisualElementAssert<S> selecting(Selecting<T, S> selector) {
        return new DefaultVisualElementAssert<S>(selector.apply(subject));
    }

    @Override
    public VisualElementsAssert<VisualElement> elements() {
        if (subject instanceof VisualElementAggregate subjects) {
            return new DefaultVisualElementsAssert<>(subjects.elements());
        } else {
            return new DefaultVisualElementsAssert<>(new VisualElement[] {subject});
        }
    }


}
