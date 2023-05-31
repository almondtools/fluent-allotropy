package net.amygdalum.allotropy.fluent.styles;

import java.util.Objects;

import net.amygdalum.allotropy.fluent.utils.MessageValues;

public record HasStyleConstraint(String value) implements StyleConstraint {

    public static StyleConstraint is(String value) {
        return new HasStyleConstraint(value);
    }

    @Override
    public boolean test(String value) {
        return Objects.equals(this.value, value);
    }

    @Override
    public String description() {
        return MessageValues.value(value);
    }

}
