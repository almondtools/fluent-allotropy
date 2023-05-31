package net.amygdalum.allotropy.fluent.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public final class InterfaceResolver {

    private InterfaceResolver() {
    }

    public static WebDriver webDriverFrom(WebElement element) {
        if (!(element instanceof WrapsDriver wrapsDriver)) {
            throw new IllegalArgumentException();
        }
        return wrapsDriver.getWrappedDriver();
    }

    public static JavascriptExecutor javascriptExecutorFrom(WebDriver webDriver) {
        if (!(webDriver instanceof JavascriptExecutor javascriptExecutor)) {
            throw new IllegalArgumentException();
        }
        return javascriptExecutor;
    }

}
