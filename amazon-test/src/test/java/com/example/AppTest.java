package com.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class AppTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chromedriver", "amazon-test/webdriver/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com");
    }

    @Test
    public void testSearchFunctionality() {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("Java Programming");
        searchBox.submit();
        Assertions.assertTrue(driver.getTitle().contains("Java Programming"));
    }

    @Test
    public void testNavigationToLoginPage() {
        WebElement accountList = driver.findElement(By.id("nav-link-accountList"));
        Actions actions = new Actions(driver);
        actions.moveToElement(accountList).perform();
        WebElement signInButton = driver.findElement(By.id("nav-flyout-ya-signin"));
        signInButton.click();
        Assertions.assertTrue(driver.getTitle().contains("Sign-In"));
    }

    @Test
    public void testAddToCart() {
        testSearchFunctionality();
        WebElement firstItem = driver.findElement(By.cssSelector(".s-result-item.s-asin"));
        firstItem.click();
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton.click();
        WebElement cart = driver.findElement(By.id("nav-cart"));
        cart.click();
        Assertions.assertTrue(driver.getTitle().contains("Shopping Cart"));
    }

    @Test
    public void testRemoveFromCart() {
        testAddToCart();
        WebElement deleteButton = driver.findElement(By.cssSelector(".sc-action-delete"));
        deleteButton.click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".sc-your-amazon-cart-is-empty")).isDisplayed());
    }

    @Test
    public void testChangeLanguageToSpanish() {
        WebElement languageDropdown = driver.findElement(By.id("icp-nav-flyout"));
        languageDropdown.click();
        WebElement spanishOption = driver.findElement(By.cssSelector("a[href*='language=es_US']"));
        spanishOption.click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("span.nav-line-2")).getText().contains("ES"));
    }

    @Test
    public void testProductReviewSection() {
        testSearchFunctionality();
        WebElement firstItem = driver.findElement(By.cssSelector(".s-result-item.s-asin"));
        firstItem.click();
        WebElement reviewsTab = driver.findElement(By.id("reviewsTabTrigger"));
        reviewsTab.click();
        Assertions.assertTrue(driver.findElement(By.id("cm_cr-review_list")).isDisplayed());
    }

    @Test
    public void testFilterByPrime() {
        testSearchFunctionality();
        WebElement primeCheckbox = driver.findElement(By.cssSelector("i.a-icon.a-icon-checkbox"));
        primeCheckbox.click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("i.a-icon.a-icon-prime")).isDisplayed());
    }

    @Test
    public void testSortByPriceLowToHigh() {
        testSearchFunctionality();
        Select sortDropdown = new Select(driver.findElement(By.id("s-result-sort-select")));
        sortDropdown.selectByValue("price-asc-rank");
        Assertions.assertTrue(driver.findElement(By.cssSelector("span.a-dropdown-label")).getText().contains("Price: Low to High"));
    }

    @Test
    public void testCustomerServicePage() {
        WebElement customerServiceLink = driver.findElement(By.linkText("Customer Service"));
        customerServiceLink.click();
        Assertions.assertTrue(driver.getTitle().contains("Customer Service"));
    }

    @Test
    public void testTodayDealsPage() {
        WebElement todaysDealsLink = driver.findElement(By.linkText("Today's Deals"));
        todaysDealsLink.click();
        Assertions.assertTrue(driver.getTitle().contains("Gold Box Deals"));
    }

    // Clean up
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
