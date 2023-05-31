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

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class LayerTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void defaultLayering() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#defaultLayering .back"));
        WebElement f = driver.findElement(By.cssSelector("#defaultLayering .front"));
        expect(b).behind().of(f);
        expect(f).inFront().of(b);
    }

    @Test
    void absoluteLayering() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#absoluteLayering .back"));
        WebElement f = driver.findElement(By.cssSelector("#absoluteLayering .front"));
        expect(b).behind().of(f);
        expect(f).inFront().of(b);
    }

    @Test
    void visibilityCollapse() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#zIndexLayering .back"));
        WebElement f = driver.findElement(By.cssSelector("#zIndexLayering .front"));
        expect(b).behind().of(f);
        expect(f).inFront().of(b);
    }

    @Test
    void noWidth() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#stackedContextsLayering .back"));
        WebElement f = driver.findElement(By.cssSelector("#stackedContextsLayering .front"));
        expect(b).behind().of(f);
        expect(f).inFront().of(b);
    }

    @Test
    void failure() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#defaultLayering .back"));
        WebElement f = driver.findElement(By.cssSelector("#defaultLayering .front"));

        AssertionError e = assertThrows(AssertionError.class, () -> expect(f).behind().of(b));
        assertThat(e.getMessage())
            .startsWith("expected [")
            .contains("] behind of [")
            .endsWith("] but was not.");

        e = assertThrows(AssertionError.class, () -> expect(b).inFront().of(f));
        assertThat(e.getMessage())
            .startsWith("expected [")
            .contains("] in front of [")
            .endsWith("] but was not.");
    }

    @Test
    void independent() {
        driver.navigate().to(server.url("/layers.html"));

        WebElement b = driver.findElement(By.cssSelector("#independent .back"));
        WebElement f = driver.findElement(By.cssSelector("#independent .front"));

        AssertionError e = assertThrows(AssertionError.class, () -> expect(f).behind().of(b));
        assertThat(e.getMessage())
            .startsWith("expected [")
            .contains("] behind of [")
            .endsWith("] but was independent.");

        e = assertThrows(AssertionError.class, () -> expect(b).inFront().of(f));
        assertThat(e.getMessage())
            .startsWith("expected [")
            .contains("] in front of [")
            .endsWith("] but was independent.");
    }

}
