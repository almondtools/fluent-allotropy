package net.amygdalum.allotropy.fluent.count;

public record GreaterCountConstraint(int count) implements CountConstraint {
    @Override
    public boolean test(Integer t) {
        return t > count;
    }

    @Override
    public String description() {
        return "> " + count;
    }
}
