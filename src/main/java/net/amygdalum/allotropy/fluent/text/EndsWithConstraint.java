package net.amygdalum.allotropy.fluent.text;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record EndsWithConstraint(String suffix) implements TextConstraint {

    public static EndsWithConstraint endsWith(String suffix) {
        return new EndsWithConstraint(suffix);
    }

    @Override
    public boolean test(String text) {
        return text.endsWith(suffix);
    }

    @Override
    public String description() {
        return "ending with " + value(suffix);
    }

}
