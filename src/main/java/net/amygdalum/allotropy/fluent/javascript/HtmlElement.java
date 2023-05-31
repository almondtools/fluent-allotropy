package net.amygdalum.allotropy.fluent.javascript;

import static net.amygdalum.allotropy.fluent.utils.InterfaceResolver.webDriverFrom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.utils.InterfaceResolver;

public class HtmlElement {

    private JavascriptExecutor js;
    private WebElement element;

    private HtmlElement(JavascriptExecutor js, WebElement element) {
        this.js = js;
        this.element = element;
    }

    public static HtmlElement from(WebElement element) {
        return from(webDriverFrom(element), element);
    }

    public static HtmlElement from(WebDriver webDriver, WebElement element) {
        return from(InterfaceResolver.javascriptExecutorFrom(webDriver), element);
    }

    public static HtmlElement from(JavascriptExecutor javascriptExecutor, WebElement element) {
        return new HtmlElement(javascriptExecutor, element);
    }

    public Bounds getBoundingClientRect() {
        return Bounds.from(js.executeScript("return arguments[0].getBoundingClientRect();", element));
    }

    public CSSStyleDeclaration getComputedStyle() {
        return CSSStyleDeclaration.from(new Value("computedStyle", "window.getComputedStyle(arguments[0])", element), js);
    }
}
