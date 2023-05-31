package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.ElementProperty.DISPLAYED;
import static net.amygdalum.allotropy.fluent.elements.ElementProperty.ENABLED;
import static net.amygdalum.allotropy.fluent.elements.ElementProperty.SELECTED;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public class WebElements {

    public static FakeWebDriver webDriver() {
        return new FakeWebDriver();
    }

    public static FakeWebElement webElement() {
        return new FakeWebElement();
    }

    public static class FakeWebDriver implements WebDriver {

        private String currentUrl;
        private String title;

        @Override
        public void get(String url) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getCurrentUrl() {
            return currentUrl;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public List<WebElement> findElements(By by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public WebElement findElement(By by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getPageSource() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
        }

        @Override
        public void quit() {
        }

        @Override
        public Set<String> getWindowHandles() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getWindowHandle() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TargetLocator switchTo() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Navigation navigate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Options manage() {
            throw new UnsupportedOperationException();
        }

    }

    public static class FakeWebElement implements WebElement, TextContainingElement, WrapsDriver {

        private FakeWebDriver driver;

        private Rectangle rect;
        private String text;
        private String tagName;
        private Set<ElementProperty> properties;
        private Map<String, String> attributes;
        private Map<String, String> cssValue;
        private Map<String, List<WebElement>> children;

        public FakeWebElement() {
            this.driver = new FakeWebDriver();
            this.properties = EnumSet.of(ENABLED, DISPLAYED);
            this.attributes = new LinkedHashMap<>();
            this.cssValue = new LinkedHashMap<>();
            this.children = new LinkedHashMap<>();
        }

        public FakeWebElement withText(String text) {
            this.text = text;
            return this;
        }

        public FakeWebElement withChild(String selector, WebElement child) {
            this.children.computeIfAbsent(selector, s -> new ArrayList<>()).add(child);
            return this;
        }

        @Override
        public String text() {
            return getText();
        }

        @Override
        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void click() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void submit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sendKeys(CharSequence... keysToSend) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getTagName() {
            return tagName;
        }

        @Override
        public String getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof By.ByCssSelector css) {
                List<WebElement> found = children.get(css.getRemoteParameters().value().toString());
                if (found != null) {
                    return found;
                }
            }
            return driver.findElements(by);
        }

        @Override
        public WebElement findElement(By by) {
            if (by instanceof By.ByCssSelector css) {
                List<WebElement> found = children.get(css.getRemoteParameters().value().toString());
                if (found != null && found.size() > 0) {
                    return found.get(0);
                }
            }
            return driver.findElement(by);
        }

        public FakeWebElement withProperties(ElementProperty property) {
            properties.add(property);
            return this;
        }

        public FakeWebElement withoutProperties(ElementProperty property) {
            properties.remove(property);
            return this;
        }

        @Override
        public boolean isSelected() {
            return properties.contains(SELECTED);
        }

        @Override
        public boolean isEnabled() {
            return properties.contains(ENABLED);
        }

        @Override
        public boolean isDisplayed() {
            return properties.contains(DISPLAYED);
        }

        @Override
        public Point getLocation() {
            return getRect().getPoint();
        }

        @Override
        public Dimension getSize() {
            return getRect().getDimension();
        }

        @Override
        public Rectangle getRect() {
            return rect;
        }

        @Override
        public String getCssValue(String propertyName) {
            return cssValue.get(propertyName);
        }

        @Override
        public WebDriver getWrappedDriver() {
            return driver;
        }

        @Override
        public String toString() {
            return text == null
                ? "[]"
                : "[" + text + "]";
        }
    }
}
