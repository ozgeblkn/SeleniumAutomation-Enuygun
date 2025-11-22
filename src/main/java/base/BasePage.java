package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

public class BasePage {
    protected WebDriver driver;
    protected WaitHelper waitHelper;
    protected Logger logger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.logger = LogManager.getLogger(this.getClass());
    }

    protected WebElement findElement(By locator) {
        return waitHelper.waitForElementVisible(locator);
    }

    protected void click(By locator) {
        waitHelper.waitForElementClickable(locator).click();
        logger.info("Clicked on element: " + locator);
    }

    protected void type(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Typed text: " + text + " into element: " + locator);
    }

    protected String getText(By locator) {
        String text = findElement(locator).getText();
        logger.info("Got text: " + text + " from element: " + locator);
        return text;
    }

    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = findElement(locator).isDisplayed();
            logger.info("Element displayed: " + displayed + " for locator: " + locator);
            return displayed;
        } catch (Exception e) {
            logger.error("Element not displayed: " + locator);
            return false;
        }
    }
}

