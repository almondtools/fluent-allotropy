package net.amygdalum.allotropy.fluent.javascript;

public record Return(String expression, Object... arguments) implements Statement {

    @Override
    public String statetment() {
        return "return " + expression + ";";
    }

}
