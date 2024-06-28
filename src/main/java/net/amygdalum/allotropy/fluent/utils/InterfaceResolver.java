package net.amygdalum.allotropy.fluent.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

public final class InterfaceResolver {

    private InterfaceResolver() {
    }

    public static WebDriver webDriverFrom(WebElement element) {
        if (element instanceof WrapsDriver wrapsDriver) {
            return wrapsDriver.getWrappedDriver();
        } else if (element instanceof WrapsElement wrapsElement) {
            return webDriverFrom(wrapsElement.getWrappedElement());
        }
        throw new IllegalArgumentException();
    }

    public static JavascriptExecutor javascriptExecutorFrom(WebDriver webDriver) {
        if (webDriver instanceof JavascriptExecutor javascriptExecutor) {
            return javascriptExecutor;
        }
        throw new IllegalArgumentException();
    }

}
