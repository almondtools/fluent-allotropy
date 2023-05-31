package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.ChromeDriverSupport;
import net.amygdalum.allotropy.fluent.LocalHttpServer;

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class SelectTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void success() {
        driver.navigate().to(server.url("/select.html"));
        WebElement[] elements = driver.findElement(By.cssSelector("#texts")).findElements(By.cssSelector(".child")).toArray(WebElement[]::new);
        expect(elements)
            .select(s -> s.findElement(By.cssSelector(".text")), s -> s.each(e -> e
                .text().startsWith("Text")));
    }

    @Test
    void failure() {
        driver.navigate().to(server.url("/select.html"));
        WebElement[] elements = driver.findElement(By.cssSelector("#different-texts")).findElements(By.cssSelector(".child")).toArray(WebElement[]::new);
        AssertionError error = assertThrows(AssertionError.class, () -> expect(elements)
            .select(s -> s.findElement(By.cssSelector(".text")), s -> s.each(e -> e
                .text().startsWith("Text"))));
        assertThat(error.getMessage())
            .startsWith("expected [")
            .endsWith("to have text starting with \"Text\" but was \"Other Text 2\".");
    }

    private AbstractStringAssert<?> assertThat(String s) {
        return org.assertj.core.api.Assertions.assertThat(s);
    }
}
