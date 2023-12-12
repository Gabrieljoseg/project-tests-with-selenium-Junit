package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.App;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.*; 
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest {

    private RemoteWebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Configura as opções do Chrome
        ChromeOptions options = new ChromeOptions();

        // Inicializa o RemoteWebDriver para se conectar ao Selenium Server no Docker
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.get("https://pt.wikipedia.org/wiki/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAcessarHomepage() {
        driver.get("https://pt.wikipedia.org/wiki/Wikipédia");
        assertEquals("Wikipédia", driver.getTitle());
    }

    @Test
    public void testPesquisarPorTermo() {
        driver.findElement(By.id("searchInput")).sendKeys("Java");
        driver.findElement(By.id("searchButton")).click();
        assertEquals("Java (linguagem de programação)", driver.findElement(By.cssSelector(".mw-headline")).getText());
    }

    private void fazerLogin(String string, String string2) {
    }
    @Test
    public void testFazerLogin() {
        fazerLogin("username", "password");
        assertEquals("Sair", driver.findElement(By.id("logoutLink")).getText());
    }

    @Test
    public void testFazerLogout() {
        fazerLogin("username", "password");
        driver.findElement(By.id("logoutLink")).click();
        assertEquals("Iniciar sessão", driver.findElement(By.id("loginLink")).getText());
    }

    @Test
    public void testCriarNovaPágina() {
        driver.findElement(By.id("createNewPage")).click();
        driver.findElement(By.id("title")).sendKeys("Título da nova página");
        driver.findElement(By.id("content")).sendKeys("Conteúdo da nova página");
        driver.findElement(By.id("saveButton")).click();
        assertEquals("Título da nova página", driver.findElement(By.cssSelector(".mw-headline")).getText());
    }

    @Test
    public void testEditarPáginaExistente() {
        driver.get("https://pt.wikipedia.org/wiki/Java");
        driver.findElement(By.id("editLink")).click();
        driver.findElement(By.id("content")).clear();
        driver.findElement(By.id("content")).sendKeys("Novo conteúdo da página");
        driver.findElement(By.id("saveButton")).click();
        assertEquals("Novo conteúdo da página", driver.findElement(By.cssSelector(".mw-content-ltr")).getText());
    }

    @Test
    public void testDeletarPágina() {
        driver.get("https://pt.wikipedia.org/wiki/Java");
        driver.findElement(By.id("deleteLink")).click();
        driver.findElement(By.id("confirmButton")).click();
        assertEquals("Página não encontrada", driver.findElement(By.cssSelector(".mw-headline")).getText());
    }

    @Test
    public void testFiltrarResultadosPesquisa() {
    Select filterType = new Select(driver.findElement(By.id("filter_type")));

    driver.findElement(By.id("searchInput")).sendKeys("Java");
    filterType.selectByValue("articles");
    driver.findElement(By.id("searchButton")).click();

    List<WebElement> results = driver.findElements(By.cssSelector(".mw-search-result-heading"));
    for (WebElement result : results) {
        assertTrue(result.getText().contains("Java"));
    }
}

    @Test
    public void testNavegarEntrePáginas() {
        driver.get("https://pt.wikipedia.org/wiki/Java");
        driver.findElement(By.linkText("Linguagem de programação")).click();
        assertEquals("Linguagem de programação", driver.findElement(By.cssSelector(".mw-headline")).getText());
    }

    @Test
    public void testSeguirDeixarSeguirUsuarios() {
        fazerLogin("username", "password");
        driver.get("https://pt.wikipedia.org/wiki/User:OutroUsuario");
        driver.findElement(By.id("followButton")).click();
        assertTrue(driver.findElement(By.id("followButton")).getText().equals("Deixar de seguir"));
        driver.findElement(By.id("followButton")).click();
        assertTrue(driver.findElement(By.id("followButton")).getText().equals("Seguir"));
    }

    @Test
    public void testComentarPágina() {
        fazerLogin("username", "password");
        driver.get("https://pt.wikipedia.org/wiki/Java");
        driver.findElement(By.id("commentInput")).sendKeys("Este é um comentário de teste.");
        driver.findElement(By.id("publishButton")).click();
        assertTrue(driver.findElement(By.cssSelector(".mw-comment-text")).getText().contains("Este é um comentário de teste."));
    }


}
