package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.directions.LayerRelation.BEHIND;
import static net.amygdalum.allotropy.fluent.directions.LayerRelation.FRONT;
import static net.amygdalum.allotropy.fluent.elements.AsVisualElement.toVisualElement;
import static net.amygdalum.allotropy.fluent.utils.AssertionErrors.expected;

import java.util.function.Function;

import net.amygdalum.allotropy.fluent.directions.LayerRelation;
import net.amygdalum.allotropy.fluent.elements.LayerableElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class DefaultLayerAssert<T extends VisualElement> implements LayerAssert<T> {

    private T subject;
    private LayerRelation relation;

    public DefaultLayerAssert(T subject, LayerRelation relation) {
        this.subject = subject;
        this.relation = relation;
    }

    @Override
    public AndAssert<T> of(Function<T, ?> selfRelative) {
        var element = selfRelative.apply(subject);
        return ofElement(toVisualElement(element));
    }

    @Override
    public AndAssert<T> ofElement(VisualElement object) {
        if (subject instanceof LayerableElement s && object instanceof LayerableElement o) {
            int comparison = s.layer().compareTo(o.layer());
            if (comparison < 0 && relation == FRONT
                || comparison > 0 && relation == BEHIND) {
                throw expected(subject)
                    .__(relation.label())
                    .of(object)
                    .butWas("not")
                    .asAssertionError();
            } else if (comparison == 0) {
                throw expected(subject)
                    .__(relation.label())
                    .of(object)
                    .butWas("independent")
                    .asAssertionError();
            }
        } else {
            throw new IllegalArgumentException("layer asserts are only possible on VisualElements with interface facet LayerableElement");
        }
        return new DefaultAndAssert<>(subject);
    }

}
