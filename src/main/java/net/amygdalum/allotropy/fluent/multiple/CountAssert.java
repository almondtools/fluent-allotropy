package net.amygdalum.allotropy.fluent.multiple;

import net.amygdalum.allotropy.fluent.count.CountConstraint;
import net.amygdalum.allotropy.fluent.count.EqCountConstraint;
import net.amygdalum.allotropy.fluent.count.GreaterCountConstraint;
import net.amygdalum.allotropy.fluent.count.LessCountConstraint;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public interface CountAssert<T extends VisualElement> {

    AndAssert<T> about(CountConstraint size);

    default AndAssert<T> equal(int count) {
        return about(new EqCountConstraint(count));
    }

    default AndAssert<T> less(int count) {
        return about(new LessCountConstraint(count));
    }

    default AndAssert<T> greater(int count) {
        return about(new GreaterCountConstraint(count));
    }

}
