package net.amygdalum.allotropy.fluent.common;

import java.util.function.Predicate;

public interface Constraint<T> extends Predicate<T> {

    String description();
}
