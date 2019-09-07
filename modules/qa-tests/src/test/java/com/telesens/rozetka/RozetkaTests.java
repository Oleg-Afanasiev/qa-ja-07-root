package com.telesens.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RozetkaTests {

    private String baseUrl;
    private WebDriver driver;

    private String mainMenuCSS = "body > app-root > div > div:nth-child(2) > app-rz-main-page > div > aside > main-page-sidebar > sidebar-fat-menu > div > ul > li:nth-child(1) > a";
    private String priceCSS = "#block_with_goods  div.g-price.g-price-cheaper > div";

    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) throws Exception {
        baseUrl = "https://rozetka.com.ua/";
        System.setProperty("webdriver.chrome.driver", "d:/distribs/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testSort() {
        driver.get(baseUrl);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(mainMenuCSS)))
                .perform();

        driver.findElement(By.partialLinkText("Мониторы")).click();
        List<String> prices =
                driver.findElements(By.cssSelector(priceCSS)).stream()
                .map(WebElement::getText)
                        .map(s->s.replaceAll("[^\\d]", ""))
                        .collect(Collectors.toList());
        System.out.println(prices);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
