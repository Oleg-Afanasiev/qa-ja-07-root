package com.telesens.automationpractice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class AuthTests {
    private static final String DEFAULT_PATH = "src/main/resources/automationpractice.properties";
    private WebDriver driver;
    private String baseUrl;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String automationPracticePath = System.getProperty("cfgAP");
        if (automationPracticePath == null)
            automationPracticePath = DEFAULT_PATH;

        Properties prop = new Properties();
        prop.load(new FileReader(automationPracticePath));
        baseUrl = prop.getProperty("base.url");
        System.setProperty("webdriver.chrome.driver", "d:/distribs/selenium/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "d:/distribs/selenium/geckodriver.exe");
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
//    @Ignore
    public void testAuthSuccess() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        WebElement element = driver.findElement(By.id("email"));
        element.click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("oleg.kh81@gmail.com");
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("123qwerty");
//         driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forgot your password?'])[1]/following::span[1]")).click();
        driver.findElement(By.id("SubmitLogin")).click(); // #SubmitLogin
//        Assert.assertEquals("", "Oleg....");
    }

    @Test
    @Ignore
    public void testAuthErrorMessage() {
        driver.get(baseUrl);
        driver.findElement( By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("WrongLogin");
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("Password");
        driver.findElement(By.id("SubmitLogin")).click();
        ////*[@id="center_column"]/div[1]/ol/li
        WebElement errorMsg = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"));
        String actualError = errorMsg.getText();
//        System.out.println(message2);
        Assert.assertEquals(actualError, "Invalid email address.");
    }

    @Test
    @Ignore
    public void testRollover() {
        driver.get(baseUrl);
        WebElement slider = driver.findElement(By.id("homeslider"));
        String[] styles = {"", "", ""};

        ExpectedCondition<Boolean> rollingComplete =
                driver1 -> {
                    // логика условия
                    System.out.println(slider.getAttribute("style"));
                    for (int i = 0; i < styles.length; i++) {
                        if (styles[i].isEmpty()) {
                            styles[i] = slider.getAttribute("style");
                            return false;
                        }
                    }


                    for (int i = 0; i < styles.length; i++) {
                        if (styles[i].equals(slider.getAttribute("style"))) {
                            return true;
                        }
                    }
                    return false;
                };

        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(3))
                .until(rollingComplete);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
