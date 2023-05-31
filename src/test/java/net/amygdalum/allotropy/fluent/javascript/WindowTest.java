package net.amygdalum.allotropy.fluent.javascript;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import net.amygdalum.allotropy.fluent.ChromeDriverSupport;
import net.amygdalum.allotropy.fluent.LocalHttpServer;

@ExtendWith(LocalHttpServer.class)
@ExtendWith(ChromeDriverSupport.class)
class WindowTest {

    private LocalHttpServer.Server server;
    private WebDriver driver;
    private Window window;

    @BeforeEach
    void before() throws Exception {
        driver.navigate().to(server.url("/js.html"));
        window = Window.from(driver);
    }

    @Test
    void testViewport() {
        Dimension size = driver.manage().window().getSize();
        int width = window.viewport().bounds().right();
        int height = window.viewport().bounds().bottom();

        driver.manage().window().setSize(new Dimension(size.width + 100, size.height - 100));

        assertThat(window.viewport().bounds().top()).isEqualTo(0);
        assertThat(window.viewport().bounds().left()).isEqualTo(0);
        assertThat(window.viewport().bounds().bottom()).isEqualTo(height - 100);
        assertThat(window.viewport().bounds().right()).isEqualTo(width + 100);
    }

}
