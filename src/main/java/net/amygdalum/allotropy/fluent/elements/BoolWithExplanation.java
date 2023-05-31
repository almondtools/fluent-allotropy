package net.amygdalum.allotropy.fluent.elements;

import net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.FalseWithExplanation;
import net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.True;

public sealed interface BoolWithExplanation permits True, FalseWithExplanation {

    public static final BoolWithExplanation TRUE = new True();

    public static BoolWithExplanation fail(String exp) {
        return new FalseWithExplanation(exp);
    }

    public static final record True() implements BoolWithExplanation {

    }

    public static final record FalseWithExplanation(String explanation) implements BoolWithExplanation {

    }

}
