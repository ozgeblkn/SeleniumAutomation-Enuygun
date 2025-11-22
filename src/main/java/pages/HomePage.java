package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By cheapFlightCheckbox = By.cssSelector("[data-testid='flight-oneWayCheckbox-checked-label']");
    
    private By originInputButton = By.cssSelector("[data-testid='flight-origin-input-comp']");
    private By originAutosuggestionInput = By.cssSelector("[data-testid='endesign-flight-origin-autosuggestion-input']");
    private By originFirstOption = By.cssSelector("[data-testid='endesign-flight-origin-autosuggestion-option-item-0']");
    
    private By destinationInputButton = By.cssSelector("[data-testid='flight-destination-input-comp']");
    private By destinationAutosuggestionInput = By.cssSelector("[data-testid='endesign-flight-destination-autosuggestion-input']");
    private By destinationFirstOption = By.cssSelector("[data-testid='endesign-flight-destination-autosuggestion-option-item-0']");
    
    private By departureDateInput = By.cssSelector("[data-testid='enuygun-homepage-flight-departureDate-datepicker-input']");
    private By returnDateLabel = By.cssSelector("[data-testid='enuygun-homepage-flight-returnDate-label']");
    private By returnDateInput = By.cssSelector("[data-testid='enuygun-homepage-flight-returnDate-datepicker-input']");
    
    private By departureMonthYearLabel = By.cssSelector("[data-testid='enuygun-homepage-flight-departureDate-month-name-and-year']");
    private By departureNextMonthButton = By.xpath("(//div[contains(@class, 'jHaclP')])[last()]");
    
    private By returnMonthYearLabel = By.cssSelector("[data-testid='enuygun-homepage-flight-returnDate-month-name-and-year']");
    private By returnNextMonthButton = By.cssSelector("[data-testid='enuygun-homepage-flight-returnDate-month-forward-button']");
    
    private By searchButton = By.cssSelector("[data-testid='enuygun-homepage-flight-submitButton']");

    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void disableCheapFlightCheckboxIfChecked() {
        try {
            logger.info("Checking if 'Ucuz bilet bul' checkbox is checked...");
            Thread.sleep(1000);
            
            org.openqa.selenium.WebElement checkbox = driver.findElement(cheapFlightCheckbox);
            
            if (checkbox != null && checkbox.isDisplayed()) {
                logger.info("'Ucuz bilet bul' checkbox is CHECKED - clicking to UNCHECK");
                checkbox.click();
                Thread.sleep(500);
                logger.info("'Ucuz bilet bul' checkbox successfully UNCHECKED");
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("'Ucuz bilet bul' checkbox is already UNCHECKED or not found");
        } catch (Exception e) {
            logger.info("Could not interact with 'Ucuz bilet bul' checkbox: " + e.getMessage());
        }
    }

    public void enterOrigin(String city) {
        click(originInputButton);
        logger.info("Clicked on origin input to open autosuggestion");
        
        waitHelper.waitForElementVisible(originAutosuggestionInput);
        type(originAutosuggestionInput, city);
        logger.info("Entered origin city: " + city);
        
        waitHelper.waitForElementVisible(originFirstOption);
        click(originFirstOption);
        logger.info("Selected first option from origin dropdown");
    }
    
    public void enterDestination(String city) {
        click(destinationInputButton);
        logger.info("Clicked on destination input to open autosuggestion");
        
        waitHelper.waitForElementVisible(destinationAutosuggestionInput);
        type(destinationAutosuggestionInput, city);
        logger.info("Entered destination city: " + city);
        
        waitHelper.waitForElementVisible(destinationFirstOption);
        click(destinationFirstOption);
        logger.info("Selected first option from destination dropdown");
    }
    
    public void selectDepartureDate(String formattedDate, String targetMonthYear) {
        click(departureDateInput);
        logger.info("Clicked on departure date input to open calendar");
        
        navigateToMonth(targetMonthYear, departureMonthYearLabel, departureNextMonthButton);
        
        By departureDayButton = By.cssSelector("button[title='" + formattedDate + "'][data-testid='datepicker-active-day']");
        waitHelper.waitForElementVisible(departureDayButton);
        click(departureDayButton);
        logger.info("Selected departure date: " + formattedDate);
    }
    
    public void selectReturnDate(String formattedDate, String targetMonthYear) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        waitHelper.waitForElementVisible(returnDateLabel);
        click(returnDateLabel);
        logger.info("Clicked on return date label to open calendar");
        
        navigateToMonth(targetMonthYear, returnMonthYearLabel, returnNextMonthButton);
        
        By returnDayButton = By.cssSelector("button[title='" + formattedDate + "'][data-testid='datepicker-active-day']");
        waitHelper.waitForElementVisible(returnDayButton);
        click(returnDayButton);
        logger.info("Selected return date: " + formattedDate);
    }
    
    private void navigateToMonth(String targetMonthYear, By monthLabelLocator, By nextButtonLocator) {
        int maxAttempts = 24;
        int attempts = 0;
        String previousMonths = "";
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (isTargetMonthVisible(targetMonthYear, monthLabelLocator)) {
            logger.info("Target month already visible: " + targetMonthYear);
            return;
        }
        
        while (attempts < maxAttempts) {
            String currentMonths = getCurrentVisibleMonths(monthLabelLocator);
            
            click(nextButtonLocator);
            logger.info("Clicked next month button, attempt: " + (attempts + 1));
            attempts++;
            
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (isTargetMonthVisible(targetMonthYear, monthLabelLocator)) {
                logger.info("Target month found after " + attempts + " clicks: " + targetMonthYear);
                return;
            }
            
            String newMonths = getCurrentVisibleMonths(monthLabelLocator);
            if (currentMonths.equals(newMonths) && !previousMonths.isEmpty()) {
                logger.warn("Calendar not advancing (reached max limit). Current: " + currentMonths);
                break;
            }
            previousMonths = currentMonths;
        }
        
        logger.warn("Could not find target month: " + targetMonthYear);
    }
    
    private String getCurrentVisibleMonths(By monthLabelLocator) {
        try {
            java.util.List<org.openqa.selenium.WebElement> monthLabels = 
                driver.findElements(monthLabelLocator);
            StringBuilder months = new StringBuilder();
            for (org.openqa.selenium.WebElement label : monthLabels) {
                months.append(label.getText()).append("|");
            }
            return months.toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    private boolean isTargetMonthVisible(String targetMonthYear, By monthLabelLocator) {
        try {
            java.util.List<org.openqa.selenium.WebElement> monthLabels = 
                driver.findElements(monthLabelLocator);
            
            for (org.openqa.selenium.WebElement label : monthLabels) {
                String monthText = label.getText().trim();
                logger.info("Checking calendar month: '" + monthText + "' vs target: '" + targetMonthYear + "'");
                
                if (monthText.equalsIgnoreCase(targetMonthYear)) {
                    logger.info("MATCH FOUND! (case insensitive)");
                    return true;
                }
                
                if (monthText.equals(targetMonthYear)) {
                    logger.info("MATCH FOUND! (exact)");
                    return true;
                }
            }
            logger.info("No match found in visible months");
            return false;
        } catch (Exception e) {
            logger.error("Error checking target month: " + e.getMessage());
            return false;
        }
    }
    
    public void clickSearchButton() {
        waitHelper.waitForElementClickable(searchButton);
        click(searchButton);
        logger.info("Clicked search button - navigating to flight results");
    }
}

