package com.telesens.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RozetkaTests {

    private String baseUrl;
    private WebDriver driver;

    private String mainMenuCSS = "body > app-root > div > div:nth-child(2) > app-rz-main-page > div > aside > main-page-sidebar > sidebar-fat-menu > div > ul > li:nth-child(1) > a";
    private String priceCSS = "div[name='goods_list_container']  div.g-price > div.g-price-uah";

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

        driver.findElement(By.cssSelector("#sort_view > a")).click();
        driver.findElement(By.xpath("//*[@id='sort_view']/div/ul/li/a[contains(text(), 'от дешевых')]")).click();

        //div[contains(@class, 'sort-popup') and contains(@style, 'visibility: hidden')]
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'sort-popup') and contains(@style, 'visibility: hidden')]")
                ));
        List<Integer> pricesSortedActual =
                driver.findElements(By.cssSelector(priceCSS)).stream()
                        .map(WebElement::getText)
                        .map(s->s.replaceAll("[^\\d]", ""))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        List<Integer> pricesSortedExpected = new ArrayList<>(pricesSortedActual);
        Collections.sort(pricesSortedExpected);


        System.out.println("pricesSortedActual: " + pricesSortedActual);
        System.out.println("pricesSortedExpected: " + pricesSortedExpected);

        Assert.assertEquals(pricesSortedActual, pricesSortedExpected);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
