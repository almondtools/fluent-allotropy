package net.amygdalum.allotropy.fluent.text;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record ContainsNotConstraint(String infix) implements TextConstraint {

    public static ContainsNotConstraint containsNot(String infix) {
        return new ContainsNotConstraint(infix);
    }

    @Override
    public boolean test(String text) {
        return !text.contains(infix);
    }

    @Override
    public String description() {
        return "not containing " + value(infix);
    }

}
