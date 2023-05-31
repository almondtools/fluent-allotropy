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
class PropertyTest {

    private WebDriver driver;
    private LocalHttpServer.Server server;

    @Nested
    class absent {
        @Test
        void displayNone() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #displayNone"));
            expect(element).isAbsent();
        }

        @Test
        void visibilityHidden() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #visibilityHidden"));
            expect(element).isAbsent();
        }

        @Test
        void visibilityCollapse() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #visibilityCollapse"));
            expect(element).isAbsent();
        }

        @Test
        void noWidth() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #noWidth"));
            expect(element).isAbsent();
        }

        @Test
        void noHeight() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #noHeight"));
            expect(element).isAbsent();
        }

        @Test
        void transparent() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #transparent"));
            expect(element).isAbsent();
        }

        @Test
        void outOfViewableArea() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #outOfView"));
            expect(element).isAbsent();
        }

        @Test
        void nestedDisplayNone() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #nestedDisplayNone"));
            expect(element).isAbsent();
        }

        @Test
        void failure() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#present #default"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(element)
                .isAbsent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be absent but was not.");
        }
    }

    @Nested
    class present {
        @Test
        void defaultVisible() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#present #default"));
            expect(element).isPresent();
        }

        @Test
        void justScrolled() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#present #justScrolled"));
            expect(element).isPresent();
        }

        @Test
        void failure() {
            driver.navigate().to(server.url("/property.html"));

            WebElement element = driver.findElement(By.cssSelector("#absent #displayNone"));
            AssertionError error = assertThrows(AssertionError.class, () -> expect(element)
                .isPresent());
            assertThat(error.getMessage())
                .startsWith("expected [")
                .endsWith("] to be present but was not.");
        }
    }

}
