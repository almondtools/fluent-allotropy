package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.utils.InterfaceResolver.webDriverFrom;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import net.amygdalum.allotropy.fluent.javascript.HtmlElement;

public class WebVisualElement implements VisualElement, AsVisualElement<WebVisualElement>, TextContainingElement, StyleableElement, LayerableElement, DisplayableElement, WrapsElement, WrapsDriver {

    private WebDriver webDriver;
    private WebElement element;

    public WebVisualElement(WebElement element) {
        this.webDriver = webDriverFrom(element);
        this.element = element;
    }

    public WebVisualElement(WebDriver webDriver, WebElement element) {
        this.webDriver = webDriver;
        this.element = element;
    }

    @Override
    public Bounds bounds() {
        return HtmlElement.from(webDriver, element).getBoundingClientRect();
    }

    @Override
    public Optional<VisualElement> parent() {
        return Optional.ofNullable(element.findElement(By.xpath("./..")))
            .map(e -> new WebVisualElement(webDriver, e));
    }

    @Override
    public WebVisualElement asVisualElement() {
        return this;
    }

    public List<WebVisualElement> findElements(By by) {
        return element.findElements(by)
            .stream()
            .map(e -> new WebVisualElement(webDriver, e))
            .toList();
    }

    public WebVisualElement findElement(By by) {
        return new WebVisualElement(webDriver, element.findElement(by));
    }

    @Override
    public String text() {
        return element.getText();
    }

    @Override
    public String style(String attribute) {
        return HtmlElement.from(webDriver, element).getComputedStyle().forAttribute(attribute);
    }

    @Override
    public Layer layer() {
        return new WebElementLayer(webDriver, element);
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return webDriver;
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    @Override
    public String toString() {
        String s = element.toString();
        return s.replaceAll("\\[[^\\[].+?\\]", "[]");
    }

}
