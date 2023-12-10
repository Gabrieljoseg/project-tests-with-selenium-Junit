package com.example;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Configura as opções do Chrome
        ChromeOptions options = new ChromeOptions();

        // Inicializa o RemoteWebDriver para se conectar ao Selenium Server no Docker
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.get("https://www.reddit.com");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testTitle() {
        String expectedTitle = "Reddit - Dive into anything";
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle, "O título da página não é o esperado.");
    }
}
