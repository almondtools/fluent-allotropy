package net.amygdalum.allotropy.fluent.utils;

public final class Exceptions {

    private Exceptions() {
    }

    public static IllegalStateException defaultInExhaustiveMatch() {
        return new IllegalStateException("default in exhaustive match");
    }

}
