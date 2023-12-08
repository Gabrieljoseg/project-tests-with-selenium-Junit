package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {

    private WebDriver driver;

    /**
     * 
     */
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/workspaces/project-tests-with-selenium-Junit/reddit-test/chrome-driver/chromedriver");
        driver = new ChromeDriver();
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
