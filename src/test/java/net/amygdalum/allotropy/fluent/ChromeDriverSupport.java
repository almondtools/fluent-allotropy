package net.amygdalum.allotropy.fluent;

import static org.junit.platform.commons.support.ReflectionSupport.findFields;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestInstances;
import org.junit.platform.commons.support.HierarchyTraversalMode;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverSupport implements BeforeEachCallback {
    static {
        try (InputStream is = ChromeDriverSupport.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (is != null) {
                LogManager.getLogManager().readConfiguration(is);
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.severe("cannot loag logging.properties: " + e.getMessage());
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Store store = context.getRoot().getStore(Namespace.create(ChromeDriverSupport.class));
        WebDriverContainer server = store.getOrComputeIfAbsent(WebDriver.class, k -> this.start(), WebDriverContainer.class);
        TestInstances testInstances = context.getRequiredTestInstances();
        for (var instance : testInstances.getAllInstances()) {
            for (var field : findFields(instance.getClass(), f -> f.getType() == WebDriver.class, HierarchyTraversalMode.BOTTOM_UP)) {
                field.setAccessible(true);
                field.set(instance, server.webDriver());
            }
        }
    }

    private WebDriverContainer start() {
        if (System.getProperty("webdriver.chrome.driver") == null) {
            WebDriverManager.chromedriver().setup();
        }
        ChromeOptions options = options();
        if (System.getProperty("browser.debug") == null) {
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            options.addArguments("disable-gpu");
        }
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(600, 400));
        return new WebDriverContainer(driver);
    }

    protected ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chromedriver");
        return options;
    }

    private record WebDriverContainer(WebDriver webDriver) implements Store.CloseableResource {

        @Override
        public void close() throws Throwable {
            webDriver.close();
        }

    }
}
