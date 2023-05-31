package net.amygdalum.allotropy.fluent.javascript;

public record Value(Variable variable, String expression, Object... arguments) implements Statement {

    public Value(String variableName, String expression, Object... arguments) {
        this(new Variable(variableName), expression, arguments);
    }

    @Override
    public String statetment() {
        return "var " + variable.name() + " = " + expression + ";";
    }

}
