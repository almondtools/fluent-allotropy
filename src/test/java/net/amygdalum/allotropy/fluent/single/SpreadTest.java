package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.ChromeDriverSupport;
import net.amygdalum.allotropy.fluent.LocalHttpServer;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class SpreadTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void success() {
        driver.navigate().to(server.url("/spread.html"));
        WebElement texts = driver.findElement(By.cssSelector("#texts"));
        expect(texts)
            .spread(s -> s.findElements(By.cssSelector(".text")).toArray(new WebVisualElement[0]), e -> e
                .count().equal(2))
            .and()
            .spread(s -> s.findElements(By.cssSelector(".text")).toArray(new WebVisualElement[0]), e -> e
                .alignedVertically()
                .left()
                .withEachOther());
    }

    @Test
    void failure() {
        driver.navigate().to(server.url("/spread.html"));
        WebElement texts = driver.findElement(By.cssSelector("#nonaligned-texts"));
        AssertionError error = assertThrows(AssertionError.class, () -> expect(texts)
            .spread(s -> ((WebVisualElement[]) s.findElements(By.cssSelector(".text")).toArray(new WebVisualElement[0])), e -> e
                .alignedVertically()
                .left()
                .withEachOther())
            .and()
            .spread(s -> s.findElements(By.cssSelector(".text")).toArray(new WebVisualElement[0]), e -> e
                .count().equal(2)));

        assertThat(error.getMessage())
            .startsWith("expected [")
            .contains(" aligned vertically at left with ")
            .endsWith(" but not at left.");
    }
}
