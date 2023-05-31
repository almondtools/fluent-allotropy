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
class SelectTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void success() {
        driver.navigate().to(server.url("/select.html"));
        WebElement compound = driver.findElement(By.cssSelector("#compound"));
        expect(compound)
            .select(s -> s.findElements(By.cssSelector("div")).get(0), e -> e
                .text().equalTo("Headline"))
            .and()
            .select(s -> s.findElements(By.cssSelector("div")).get(1), e -> e
                .text().contains("Body"));
    }

    @Test
    void failure() {
        driver.navigate().to(server.url("/select.html"));
        WebElement compound = driver.findElement(By.cssSelector("#compound"));
        AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
            .select(s -> s.findElements(By.cssSelector("div")).get(0), e -> e
                .text().equalTo("Headline"))
            .and()
            .select(s -> s.findElements(By.cssSelector("div")).get(1), e -> e
                .text().equalTo("Text")));
        assertThat(error.getMessage())
            .startsWith("expected [")
            .endsWith(" to have text \"Text\" but was \"Body\".");
    }
}
