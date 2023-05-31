package net.amygdalum.allotropy.fluent.text;

import static net.amygdalum.allotropy.fluent.utils.MessageValues.value;

public record StartsWithConstraint(String prefix) implements TextConstraint {

    public static StartsWithConstraint startsWith(String prefix) {
        return new StartsWithConstraint(prefix);
    }

    @Override
    public boolean test(String text) {
        return text.startsWith(prefix);
    }

    @Override
    public String description() {
        return "starting with " + value(prefix);
    }

}
