package net.amygdalum.allotropy.fluent.count;

public record EqCountConstraint(int count) implements CountConstraint {

    @Override
    public boolean test(Integer t) {
        return count == t;
    }

    @Override
    public String description() {
        return String.valueOf(count);
    }

}
