package net.amygdalum.allotropy.fluent.text;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record ContainsConstraint(String infix) implements TextConstraint {

    public static ContainsConstraint contains(String infix) {
        return new ContainsConstraint(infix);
    }

    @Override
    public boolean test(String text) {
        return text.contains(infix);
    }

    @Override
    public String description() {
        return "containing " + value(infix);
    }

}
