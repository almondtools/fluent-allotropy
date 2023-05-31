package net.amygdalum.allotropy.fluent.javascript;

import static net.amygdalum.allotropy.fluent.utils.InterfaceResolver.javascriptExecutorFrom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import net.amygdalum.allotropy.fluent.elements.Viewport;

public class Window {

    private JavascriptExecutor js;

    private Window(JavascriptExecutor js) {
        this.js = js;
    }

    public static Window from(WebDriver driver) {
        return from(javascriptExecutorFrom(driver));
    }

    public static Window from(JavascriptExecutor js) {
        return new Window(js);
    }
    
    public Viewport viewport() {
        return Viewport.from(js.executeScript("return {"
            + "  width: document.documentElement.clientWidth || document.body.clientWidth|| window.innerWidth,"
            + "  height: document.documentElement.clientHeight|| document.body.clientHeight|| window.innerHeight"
            + "};"));    
    }

    
}
