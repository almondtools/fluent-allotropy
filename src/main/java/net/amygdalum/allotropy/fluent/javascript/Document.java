package net.amygdalum.allotropy.fluent.javascript;

import static net.amygdalum.allotropy.fluent.utils.InterfaceResolver.javascriptExecutorFrom;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Document {

    private JavascriptExecutor js;

    private Document(JavascriptExecutor js) {
        this.js = js;
    }

    public static Document from(WebDriver driver) {
        return from(javascriptExecutorFrom(driver));
    }

    public static Document from(JavascriptExecutor js) {
        return new Document(js);
    }
    
    public WebElement elementFromPoint(double x, double y) {
        return (WebElement) js.executeScript("return document.elementFromPoint(arguments[0], arguments[1]);", x,y);
    }

    public List<WebElement> elementsFromPoint(double x, double y) {
        @SuppressWarnings("unchecked")
        List<WebElement> elements = (List<WebElement>) js.executeScript("return document.elementsFromPoint(arguments[0], arguments[1]);", x,y);
        return elements;
    }

    
}
