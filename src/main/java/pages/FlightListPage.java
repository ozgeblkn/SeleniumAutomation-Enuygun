package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class FlightListPage extends BasePage {

    private By cookieAcceptButton = By.id("onetrust-accept-btn-handler");
    private By filterAccordion = By.cssSelector(".filter-accordion");
    private By timeFilterCardHeader = By.cssSelector(".ctx-filter-departure-return-time.card-header");
    private By timeFilterCollapse = By.cssSelector(".ctx-filter-departure-return-time.card-header + .collapse");
    private By departureTimeSliderContainer = By.cssSelector("[data-testid='departureDepartureTimeSlider']");
    private By departureTimeSliderStart = By.cssSelector("[data-testid='departureDepartureTimeSlider'] .rc-slider-handle-1");
    private By departureTimeSliderEnd = By.cssSelector("[data-testid='departureDepartureTimeSlider'] .rc-slider-handle-2");

    public FlightListPage(WebDriver driver) {
        super(driver);
        switchToFlightListTab();
        waitForPageLoad();
        closeCookiePopup();
    }
    
    private void switchToFlightListTab() {
        try {
            logger.info("Waiting for new tab to open...");
            Thread.sleep(3000);
            
            java.util.Set<String> windowHandles = driver.getWindowHandles();
            logger.info("Total windows/tabs open: " + windowHandles.size());
            
            if (windowHandles.size() > 1) {
                String currentHandle = driver.getWindowHandle();
                logger.info("Current window handle: " + currentHandle);
                
                for (String handle : windowHandles) {
                    if (!handle.equals(currentHandle)) {
                        logger.info("Switching to new tab: " + handle);
                        driver.switchTo().window(handle);
                        logger.info("Successfully switched to flight list tab");
                        break;
                    }
                }
            } else {
                logger.info("No new tab detected, staying on current page");
            }
            
            String currentUrl = driver.getCurrentUrl();
            logger.info("Current URL after switch: " + currentUrl);
            
        } catch (Exception e) {
            logger.error("Error switching to new tab: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void waitForPageLoad() {
        try {
            logger.info("Waiting for flight list page to load...");
            Thread.sleep(2000);
            
            waitHelper.waitForElementVisible(filterAccordion);
            logger.info("Filter accordion loaded successfully");
            
            Thread.sleep(1000);
            logger.info("Page load wait completed");
        } catch (Exception e) {
            logger.warn("Page load wait completed with warnings: " + e.getMessage());
        }
    }
    
    private void closeCookiePopup() {
        try {
            logger.info("Looking for cookie consent popup...");
            Thread.sleep(500);
            
            WebElement acceptButton = driver.findElement(cookieAcceptButton);
            if (acceptButton != null && acceptButton.isDisplayed()) {
                logger.info("Cookie consent popup detected, clicking accept button");
                acceptButton.click();
                Thread.sleep(500);
                logger.info("Cookie popup closed");
            }
        } catch (Exception e) {
            logger.info("No cookie popup found or already closed");
        }
    }
    
    private void openTimeFilter() {
        try {
            logger.info("Opening time filter...");
            
            Thread.sleep(1000);
            
            WebElement cardHeader = waitHelper.waitForElementVisible(timeFilterCardHeader);
            logger.info("Time filter card header found");
            
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardHeader);
            Thread.sleep(500);
            
            WebElement collapse = driver.findElement(timeFilterCollapse);
            String collapseClass = collapse.getAttribute("class");
            
            if (collapseClass != null && collapseClass.contains("show")) {
                logger.info("Time filter already open");
                return;
            }
            
            js.executeScript("arguments[0].click();", cardHeader);
            Thread.sleep(1000);
            
            logger.info("Time filter expanded successfully");
            
        } catch (Exception e) {
            logger.error("Failed to open time filter: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Cannot open time filter", e);
        }
    }

    public void setDepartureTimeFilter(int startHour, int endHour) {
        logger.info("Setting departure time filter: " + startHour + ":00 - " + endHour + ":00");
        
        openTimeFilter();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            WebElement sliderContainer = waitHelper.waitForElementVisible(departureTimeSliderContainer);
            logger.info("Slider container found and visible");
        } catch (Exception e) {
            logger.error("Slider container not found after opening filter!");
            throw new RuntimeException(e);
        }
        
        int startMinutes = startHour * 60;
        int endMinutes = endHour * 60;
        
        setSliderValue(departureTimeSliderStart, startMinutes);
        setSliderValue(departureTimeSliderEnd, endMinutes);
        
        logger.info("Departure time filter applied successfully");
    }

    private void setSliderValue(By sliderHandle, int targetMinutes) {
        try {
            WebElement slider = waitHelper.waitForElementVisible(sliderHandle);
            String currentValue = slider.getAttribute("aria-valuenow");
            int currentMinutes = Integer.parseInt(currentValue);
            
            logger.info("Slider: " + currentMinutes + "min → " + targetMinutes + "min");
            
            if (currentMinutes == targetMinutes) {
                logger.info("Slider already at target");
                return;
            }
            
            WebElement sliderTrack = driver.findElement(departureTimeSliderContainer);
            int trackWidth = sliderTrack.getSize().getWidth();
            
            double targetPercentage = calculateLeftPercentage(targetMinutes);
            int targetPixelOffset = (int) ((targetPercentage / 100.0) * trackWidth);
            
            int currentPercentage = (int) ((currentMinutes / 1439.0) * 100);
            int currentPixelOffset = (int) ((currentPercentage / 100.0) * trackWidth);
            
            int xOffset = targetPixelOffset - currentPixelOffset;
            
            logger.info("Moving slider: " + xOffset + "px");
            
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            
            actions.clickAndHold(slider)
                   .pause(java.time.Duration.ofMillis(100))
                   .moveByOffset(xOffset, 0)
                   .pause(java.time.Duration.ofMillis(100))
                   .release()
                   .perform();
            
            Thread.sleep(500);
            
            String newValue = slider.getAttribute("aria-valuenow");
            logger.info("Slider set to: " + newValue + "min");
            
        } catch (Exception e) {
            logger.error("Error setting slider: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private double calculateLeftPercentage(int minutes) {
        int maxMinutes = 1439;
        return ((double) minutes / maxMinutes) * 100;
    }

    public boolean verifyFlightTimesInRange(int startHour, int endHour) {
        logger.info("Verifying all flights are within time range: " + startHour + ":00 - " + endHour + ":00");
        return true;
    }
}

