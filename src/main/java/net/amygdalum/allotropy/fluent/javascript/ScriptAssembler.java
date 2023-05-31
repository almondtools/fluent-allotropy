package net.amygdalum.allotropy.fluent.javascript;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

public class ScriptAssembler {

    private List<Object> arguments;
    private StringBuilder code;

    public ScriptAssembler() {
        this.arguments = new ArrayList<>();
        this.code = new StringBuilder();
    }
    public ScriptAssembler addStatement(Statement stmt) {
        String code = stmt.statetment();
        Object[] args = stmt.arguments();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Variable v) {
                code = code.replace("arguments[" + i+"]", v.name());
            } else {
                int index = arguments.size();
                arguments.add(args[i]);
                code = code.replace("arguments[" + i+"]", "$[" + index + "]");
            }
        }
        this.code.append(code);
        return this;
    }

    public <T> T executeWith(JavascriptExecutor js, Class<T> expected) {
        String code = this.code.toString().replace("$", "arguments");
        Object result = js.executeScript(code, arguments.toArray(Object[]::new));
        return expected.cast(result);
    }

}
