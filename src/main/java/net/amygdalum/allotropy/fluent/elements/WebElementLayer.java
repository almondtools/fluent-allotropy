package net.amygdalum.allotropy.fluent.elements;

import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.javascript.Document;
import net.amygdalum.allotropy.fluent.javascript.HtmlElement;

public class WebElementLayer implements Layer {

    private WebDriver webDriver;
    private WebElement element;

    public WebElementLayer(WebDriver webDriver, WebElement element) {
        this.webDriver = webDriver;
        this.element = element;
    }

    @Override
    public int compareTo(Layer o) {
        if (!(o instanceof WebElementLayer that)) {
            return 0;
        }
        Bounds thisRect = HtmlElement.from(this.webDriver, this.element).getBoundingClientRect();
        Bounds thatRect = HtmlElement.from(that.webDriver, that.element).getBoundingClientRect();
        Point point = Stream.of(thisRect.topLeft(), thisRect.topRight(), thisRect.bottomRight(), thisRect.bottomLeft())
            .filter(p -> thatRect.contains(p))
            .findFirst()
            .or(() -> Stream.of(thatRect.topLeft(), thatRect.topRight(), thatRect.bottomRight(), thatRect.bottomLeft())
                .filter(p -> thisRect.contains(p))
                .findFirst())
            .orElse(null);
        if (point == null) {
            return 0;
        }
        List<WebElement> elementsAtPoint = Document.from(webDriver).elementsFromPoint(point.x(), point.y());
        int thisIndex = elementsAtPoint.indexOf(this.element);
        int thatIndex = elementsAtPoint.indexOf(that.element);
        if (thisIndex == -1 || thatIndex == -1) {
            return 0;
        } else {
            return thatIndex - thisIndex;
        }
    }

}
