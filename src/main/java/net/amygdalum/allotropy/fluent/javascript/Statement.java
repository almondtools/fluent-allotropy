package net.amygdalum.allotropy.fluent.javascript;

public sealed interface Statement permits Value, Return {

    String statetment();

    Object[] arguments();
}
