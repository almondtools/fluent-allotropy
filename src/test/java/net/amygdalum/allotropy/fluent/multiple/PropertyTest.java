package net.amygdalum.allotropy.fluent.multiple;

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
class PropertyTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Nested
    class absent {
        @Test
        void all() {
            driver.navigate().to(server.url("/property.html"));

            WebElement[] elements = driver.findElements(By.cssSelector("#absent div")).toArray(WebElement[]::new);
            expect(elements).areAbsent();
        }

        @Test
        void partially() {
            driver.navigate().to(server.url("/property.html"));

            WebElement absent = driver.findElement(By.cssSelector("#absent #visibilityHidden"));
            WebElement present = driver.findElement(By.cssSelector("#present #default"));
         
            AssertionError error = assertThrows(AssertionError.class, () -> expect(absent, present)
                .areAbsent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be absent but was not.");
        }

        @Test
        void none() {
            driver.navigate().to(server.url("/property.html"));

            WebElement[] elements = driver.findElements(By.cssSelector("#present div")).toArray(WebElement[]::new);
            
            AssertionError error = assertThrows(AssertionError.class, () -> expect(elements)
                .areAbsent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be absent but was not.");
        }

    }

    @Nested
    class present {
        @Test
        void all() {
            driver.navigate().to(server.url("/property.html"));

            WebElement[] elements = driver.findElements(By.cssSelector("#present div")).toArray(WebElement[]::new);
            expect(elements).arePresent();
        }

        @Test
        void partially() {
            driver.navigate().to(server.url("/property.html"));

            WebElement absent = driver.findElement(By.cssSelector("#absent #visibilityHidden"));
            WebElement present = driver.findElement(By.cssSelector("#present #default"));
         
            AssertionError error = assertThrows(AssertionError.class, () -> expect(absent, present)
                .arePresent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be present but was not.");
        }

        @Test
        void none() {
            driver.navigate().to(server.url("/property.html"));

            WebElement[] elements = driver.findElements(By.cssSelector("#absent div")).toArray(WebElement[]::new);
            
            AssertionError error = assertThrows(AssertionError.class, () -> expect(elements)
                .arePresent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be present but was not.");
        }

    }

}
