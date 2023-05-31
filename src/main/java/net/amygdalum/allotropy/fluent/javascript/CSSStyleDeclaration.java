package net.amygdalum.allotropy.fluent.javascript;

import org.openqa.selenium.JavascriptExecutor;

public record CSSStyleDeclaration(Value value, JavascriptExecutor js) {

    public static CSSStyleDeclaration from(Value value, JavascriptExecutor js) {
        return new CSSStyleDeclaration(value, js);
    }

    public String forAttribute(String attribute) {
        return new ScriptAssembler()
            .addStatement(value)
            .addStatement(new Return("arguments[0].getPropertyValue(arguments[1])", value.variable(), attribute))
            .executeWith(js, String.class);
    }

}
