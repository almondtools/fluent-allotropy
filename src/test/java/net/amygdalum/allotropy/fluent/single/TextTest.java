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
class TextTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Test
    void success() {
        driver.navigate().to(server.url("/text.html"));
        WebElement compound = driver.findElement(By.cssSelector("#with-text"));
        expect(compound)
            .text()
            .startsWith("Here")
            .contains("is some")
            .endsWith("text.")
            .containsNot("Text")
            .and()
            .text()
            .equalTo("Here is some text.");

    }

    @Test
    void mapped() {
        driver.navigate().to(server.url("/text.html"));
        WebElement compound = driver.findElement(By.cssSelector("#with-text"));
        expect(compound)
            .text()
            .mappedTo(String::toLowerCase)
            .equalTo("here is some text.");
    }

    @Nested
    class failure {

        @Test
        void startsWith() {
            driver.navigate().to(server.url("/text.html"));
            WebElement compound = driver.findElement(By.cssSelector("#with-text"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .text()
                .startsWith("here"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text starting with \"here\" but was \"Here is some text.\".");
        }

        @Test
        void endsWith() {
            driver.navigate().to(server.url("/text.html"));
            WebElement compound = driver.findElement(By.cssSelector("#with-text"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .text()
                .endsWith("Text!"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text ending with \"Text!\" but was \"Here is some text.\".");
        }

        @Test
        void contains() {
            driver.navigate().to(server.url("/text.html"));
            WebElement compound = driver.findElement(By.cssSelector("#with-text"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .text()
                .contains("Text"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text containing \"Text\" but was \"Here is some text.\".");
        }

        @Test
        void containsNot() {
            driver.navigate().to(server.url("/text.html"));
            WebElement compound = driver.findElement(By.cssSelector("#with-text"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .text()
                .containsNot("some text"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text not containing \"some text\" but was \"Here is some text.\".");
        }

        @Test
        void equalTo() {
            driver.navigate().to(server.url("/text.html"));
            WebElement compound = driver.findElement(By.cssSelector("#with-text"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .text()
                .equalTo("Text"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text \"Text\" but was \"Here is some text.\".");
        }
    }
}
