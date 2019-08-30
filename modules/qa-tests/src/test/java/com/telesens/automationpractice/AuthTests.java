package com.telesens.automationpractice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class AuthTests {
    private WebDriver driver;
    private String baseUrl = "http://automationpractice.com/index.php";

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "d:/distribs/selenium/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "d:/distribs/selenium/geckodriver.exe");
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAuthSuccess() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("oleg.kh81@gmail.com");
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("123qwerty");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forgot your password?'])[1]/following::span[1]")).click();
        driver.findElement(By.linkText("Sign out")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
