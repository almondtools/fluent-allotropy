package net.amygdalum.allotropy.fluent.styles;

import net.amygdalum.allotropy.fluent.utils.MessageValues;

public record ContainsStyleConstraint(String value) implements StyleConstraint {

    public static StyleConstraint contains(String value) {
        return new ContainsStyleConstraint(value);
    }

    @Override
    public boolean test(String value) {
        return this.value != null
            && value.contains(this.value);
    }

    @Override
    public String description() {
        return "containing " + MessageValues.value(this.value);
    }

}
