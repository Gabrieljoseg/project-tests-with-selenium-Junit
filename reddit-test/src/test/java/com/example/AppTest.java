package com.example;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.*;
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
        driver.findElement(By.id("searchInput")).sendKeys("Java");
        driver.findElement(By.id("filter_type")).selectByValue("articles");
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
