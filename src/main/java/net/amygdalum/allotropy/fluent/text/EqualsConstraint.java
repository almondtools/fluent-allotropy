package net.amygdalum.allotropy.fluent.text;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record EqualsConstraint(String template) implements TextConstraint {

    public static EqualsConstraint equals(String template) {
        return new EqualsConstraint(template);
    }

    @Override
    public boolean test(String text) {
        return text.equals(template);
    }

    @Override
    public String description() {
        return value(template);
    }

}
