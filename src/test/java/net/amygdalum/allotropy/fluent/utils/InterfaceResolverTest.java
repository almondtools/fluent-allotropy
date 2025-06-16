package net.amygdalum.allotropy.fluent.utils;

import static net.amygdalum.allotropy.fluent.utils.InterfaceResolver.webDriverFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import net.amygdalum.allotropy.fluent.elements.WebElements;
import net.amygdalum.allotropy.fluent.elements.WebElements.FakeWebDriver;

class InterfaceResolverTest {

    @Nested
    class webDriverFrom {
        @Test
        void unwrapsWrapsDriver() {
            WebElement element = WebElements.webElement();
            var driver = webDriverFrom(element);
            assertThat(driver).isInstanceOf(FakeWebDriver.class);
        }

        @Test
        void unwrapsWrapsElement() {
            WebElement element = new WrappingElement(WebElements.webElement());
            var driver = webDriverFrom(element);
            assertThat(driver).isInstanceOf(FakeWebDriver.class);
        }

        @Test
        void failing() {
            WebElement element = new NonWrappingElement(WebElements.webElement());
            assertThrows(IllegalArgumentException.class, () -> webDriverFrom(element));
        }
    }

    class BaseWebElement implements WebElement {
        protected WebElement element;

        public BaseWebElement(WebElement element) {
            this.element = element;
        }

        @Override
        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            return element.getScreenshotAs(target);
        }

        @Override
        public void click() {
            element.click();
        }

        @Override
        public void submit() {
            element.submit();
        }

        @Override
        public void sendKeys(CharSequence... keysToSend) {
            element.sendKeys(keysToSend);
        }

        @Override
        public void clear() {
            element.clear();
        }

        @Override
        public String getTagName() {
            return element.getTagName();
        }

        @Override
        public @Nullable String getAttribute(String name) {
            return element.getAttribute(name);
        }

        @Override
        public boolean isSelected() {
            return element.isSelected();
        }

        @Override
        public boolean isEnabled() {
            return element.isEnabled();
        }

        @Override
        public String getText() {
            return element.getText();
        }

        @Override
        public List<WebElement> findElements(By by) {
            return element.findElements(by);
        }

        @Override
        public WebElement findElement(By by) {
            return element.findElement(by);
        }

        @Override
        public boolean isDisplayed() {
            return element.isDisplayed();
        }

        @Override
        public Point getLocation() {
            return element.getLocation();
        }

        @Override
        public Dimension getSize() {
            return element.getSize();
        }

        @Override
        public Rectangle getRect() {
            return element.getRect();
        }

        @Override
        public String getCssValue(String propertyName) {
            return element.getCssValue(propertyName);
        }

    }

    class WrappingElement extends BaseWebElement implements WrapsElement {

        public WrappingElement(WebElement element) {
            super(element);
        }

        @Override
        public WebElement getWrappedElement() {
            return element;
        }
    }

    class NonWrappingElement extends BaseWebElement {

        public NonWrappingElement(WebElement element) {
            super(element);
        }
    }
}
