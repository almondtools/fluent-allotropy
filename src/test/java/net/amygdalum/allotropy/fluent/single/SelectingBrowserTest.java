package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.single.Selecting.all;
import static net.amygdalum.allotropy.fluent.single.Selecting.by;
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
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class SelectingBrowserTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Nested
    class testGeneric {
        @Test
        void success() {
            driver.navigate().to(server.url("/select.html"));
            WebElement compound = driver.findElement(By.cssSelector("#compound"));
            expect(compound)
                .selecting(s -> s.findElements(By.cssSelector("div")).get(0)).text().equalTo("Headline")
                .and().backAs(WebVisualElement.class)
                .selecting(s -> s.findElements(By.cssSelector("div")).get(1)).text().contains("Body");
        }

        @Test
        void failure() {
            driver.navigate().to(server.url("/select.html"));
            WebElement compound = driver.findElement(By.cssSelector("#compound"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .selecting(s -> s.findElements(By.cssSelector("div")).get(0)).text().equalTo("Headline")
                .and().backAs(WebVisualElement.class)
                .selecting(s -> s.findElements(By.cssSelector("div")).get(1)).text().equalTo("Text"));
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith(" to have text \"Text\" but was \"Body\".");
        }
    }

    @Nested
    class testBy {
        @Test
        void success() {
            driver.navigate().to(server.url("/select.html"));
            WebElement compound = driver.findElement(By.cssSelector("#texts"));
            expect(compound)
                .selecting(by(s -> s.findElement(By.cssSelector(".text"))))
                .isPresent();
        }

        @Test
        void failure() {
            driver.navigate().to(server.url("/select.html"));
            WebElement compound = driver.findElement(By.cssSelector("#texts"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(compound)
                .selecting(by(s -> s.findElement(By.cssSelector(".html"))))
                .isPresent());
            assertThat(error.getMessage())
            .isEqualTo("expected null to be present but was not.");
        }

    }

    @Nested
    class testAll {
        @Test
        void success() {
            driver.navigate().to(server.url("/spread.html"));
            WebElement texts = driver.findElement(By.cssSelector("#texts"));
            expect(texts)
                .selecting(all(s -> s.findElements(By.cssSelector(".text")))).elements()
                .count().equal(2)
                .and()
                .alignedVertically()
                .left()
                .withEachOther();
        }

        @Test
        void failure() {
            driver.navigate().to(server.url("/spread.html"));
            WebElement texts = driver.findElement(By.cssSelector("#nonaligned-texts"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(texts)
                .selecting(all(s -> s.findElements(By.cssSelector(".text")))).elements()
                .alignedVertically()
                .left()
                .withEachOther()
                .and()
                .count().equal(2));

            assertThat(error.getMessage())
                .startsWith("expected [")
                .contains(" aligned vertically at left with ")
                .endsWith(" but not at left.");
        }

    }
}
