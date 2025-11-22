package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.DriverManager;

public class BaseTest {
    protected WebDriver driver;
    protected Logger logger;

    @BeforeMethod
    public void setUp() {
        logger = LogManager.getLogger(this.getClass());
        logger.info("Starting test...");
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getBaseUrl());
        logger.info("Navigated to: " + ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Closing browser...");
        DriverManager.quitDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }
}

