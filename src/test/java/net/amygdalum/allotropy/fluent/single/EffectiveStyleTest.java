package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.ChromeDriverSupport;
import net.amygdalum.allotropy.fluent.LocalHttpServer;

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class EffectiveStyleTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void elementStyle() {
        driver.navigate().to(server.url("/style.html"));

        WebElement element = driver.findElement(By.cssSelector("#elementStyle"));
        expect(element)
            .effectiveStyle().attribute("font-size").is("15px")
            .and()
            .effectiveStyle().attribute("font-family").is("Arial");
    }

    @Test
    void cssStyle() {
        driver.navigate().to(server.url("/style.html"));

        WebElement element = driver.findElement(By.cssSelector("#cssStyle"));
        expect(element).effectiveStyle().attribute("font-size").is("12px");
    }

    @Test
    void inheritedStyle() {
        driver.navigate().to(server.url("/style.html"));

        WebElement element = driver.findElement(By.cssSelector("#inheritedStyle"));
        expect(element).effectiveStyle().attribute("font-size").is("23px");
    }

    @Test
    void inheritedRelativeStyle() {
        driver.navigate().to(server.url("/style.html"));

        WebElement element = driver.findElement(By.cssSelector("#inheritedRelativeStyle"));
        expect(element).effectiveStyle().attribute("font-size").is("40px");
    }

    @Test
    void mapped() {
        driver.navigate().to(server.url("/style.html"));

        WebElement element = driver.findElement(By.cssSelector("#elementStyle"));
        expect(element).effectiveStyle().attribute("font-family")
            .mappedTo(String::toLowerCase)
            .contains("arial");
    }

    @Nested
    class failure {
        @Test
        void is() {
            driver.navigate().to(server.url("/style.html"));

            WebElement element = driver.findElement(By.cssSelector("#elementStyle"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(element).effectiveStyle().attribute("font-size").contains("em"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to have font-size containing \"em\" but found \"15px\".");
        }

        @Test
        void contains() {
            driver.navigate().to(server.url("/style.html"));

            WebElement element = driver.findElement(By.cssSelector("#elementStyle"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(element).effectiveStyle().attribute("font-size").is("20px"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to have font-size \"20px\" but found \"15px\".");
        }
    }
}
