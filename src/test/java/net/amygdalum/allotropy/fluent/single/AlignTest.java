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
class AlignTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void success() {
        driver.navigate().to(server.url("/align.html"));
        WebElement v1 = driver.findElement(By.cssSelector("#vertical .text1"));
        WebElement v2 = driver.findElement(By.cssSelector("#vertical .text2"));
        WebElement h1 = driver.findElement(By.cssSelector("#horizontal .text1"));
        WebElement h2 = driver.findElement(By.cssSelector("#horizontal .text2"));
        expect(v1).alignedVertically().with(v2);
        expect(h1).alignedHorizontally().with(h2);
    }

    @Test
    void failure() {
        driver.navigate().to(server.url("/align.html"));
        WebElement v1 = driver.findElement(By.cssSelector("#vertical .text1"));
        WebElement v2 = driver.findElement(By.cssSelector("#vertical .text2"));
        WebElement h1 = driver.findElement(By.cssSelector("#horizontal .text1"));
        WebElement h2 = driver.findElement(By.cssSelector("#horizontal .text2"));
        AssertionError hError = assertThrows(AssertionError.class, () -> expect(v1).alignedHorizontally().with(v2));
        assertThat(hError.getMessage())
            .startsWith("expected [")
            .endsWith(" but was overlapping vertically.");

        AssertionError vError = assertThrows(AssertionError.class, () -> expect(h1).alignedVertically().with(h2));
        assertThat(vError.getMessage())
            .startsWith("expected [")
            .endsWith(" but was overlapping horizontally.");
    }
}
