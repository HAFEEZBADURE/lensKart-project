package com.w2a.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;

    // Loggers
    public static Logger log = Logger.getLogger("appLog");
    public static Logger seleniumLog = Logger.getLogger("seleniumLogger");

    public static String DRIVER_DIR = System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\";
    public static String CHROME_DRIVER_PATH = DRIVER_DIR + "chromedriver.exe";
    public static String GECKO_DRIVER_PATH = DRIVER_DIR + "geckodriver.exe";


    @BeforeSuite
    public void setup() {
        log.info("============= Test Execution Started =============");

        if (driver == null) {
            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
                config.load(fis);
                log.debug("Loaded Config.properties");
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
                OR.load(fis);
                log.debug("Loaded OR.properties");

            } catch (FileNotFoundException e) {
                log.error("Properties file not found", e);
            } catch (IOException e) {
                log.error("Failed to load properties", e);
            }

            // Initialize browser
            if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                driver = new ChromeDriver();
                log.info("Chrome browser launched");
            } else if (config.getProperty("browser").equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", GECKO_DRIVER_PATH);
                driver = new FirefoxDriver();
                log.info("Firefox browser launched");
            } else {
                throw new RuntimeException("Unsupported browser: " + config.getProperty("browser"));
            }

            // Navigate
            String testUrl = config.getProperty("testsiteurl");
            driver.get(testUrl);
            driver.manage().window().maximize();
            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
            log.info("Navigated to: " + testUrl);
        }

        log.info("============= Test Setup Complete =============");
    }

    public boolean isElementPresent(By by) {

        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
        log.info("============= Test Execution Completed =============");
    }

    public String captureScreenshot(String screenshotName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dest = "test-output/screenshots/" + screenshotName + ".png";
            File destFile = new File(dest);
            destFile.getParentFile().mkdirs();
            java.nio.file.Files.copy(src.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
