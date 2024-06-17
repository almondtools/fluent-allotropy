package net.amygdalum.allotropy.fluent;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.chunks.Chunks.sized;
import static org.openqa.selenium.By.tagName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v114.css.model.CSSProperty;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.CanvasVisualElement;
import net.amygdalum.allotropy.fluent.chunks.Chunks;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;

@ExtendWith(CanvasExtension.class)
class DemoTest {

    @Canvas(print = true)
    public AsciiCanvas canvas;

    @Test
    void success() {
        WebVisualElement item1 = null;//canvas.rect(0, 0, 0, 0);
        var item2 = canvas.rect(0, 0, 0, 0);
        var item3 = canvas.rect(0, 0, 0, 0);
        var items = new CanvasVisualElement[] {item1, item2};
        expect(items)
            .chunked(sized(2), e -> e.alignedHorizontally().withEachOther());

    }

}
