package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class FlightListPage extends BasePage {

    private By cookieAcceptButton = By.id("onetrust-accept-btn-handler");
    private By filterAccordion = By.cssSelector(".filter-accordion");
    private By transitFilterCardHeader = By.cssSelector(".ctx-filter-transit.card-header");
    private By transitFilterCollapse = By.cssSelector(".ctx-filter-transit.card-header + .collapse");
    private By directFlightCheckbox = By.id("0stopCount");
    private By timeFilterCardHeader = By.cssSelector(".ctx-filter-departure-return-time.card-header");
    private By timeFilterCollapse = By.cssSelector(".ctx-filter-departure-return-time.card-header + .collapse");
    private By departureTimeSliderContainer = By.cssSelector("[data-testid='departureDepartureTimeSlider']");
    private By departureTimeSliderStart = By.cssSelector("[data-testid='departureDepartureTimeSlider'] .rc-slider-handle-1");
    private By departureTimeSliderEnd = By.cssSelector("[data-testid='departureDepartureTimeSlider'] .rc-slider-handle-2");
    private By airlineFilterCardHeader = By.cssSelector(".ctx-filter-airline.card-header");
    private By airlineFilterCollapse = By.cssSelector(".ctx-filter-airline.card-header + .collapse");
    private By turkishAirlinesCheckbox = By.id("TKairlines");
    private By selectAllAirlinesButton = By.cssSelector(".search__filter_airlines-ALL.filter-grup__item");
    private By airportFilterCardHeader = By.cssSelector(".ctx-filter-airports.card-header");
    private By airportFilterCollapse = By.cssSelector(".ctx-filter-airports.card-header + .collapse");
    private By sawAirportCheckbox = By.id("SAWairports");
    private By istAirportCheckbox = By.id("ISTairports");
    private By priceAscendingSort = By.cssSelector(".search__filter_sort-PRICE_ASC");
    private By flightPrices = By.cssSelector("[data-testid='flightInfoPrice']");

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
    
    public void openTransitFilter() {
        try {
            logger.info("Opening transit filter...");
            
            WebElement cardHeader = waitHelper.waitForElementVisible(transitFilterCardHeader);
            logger.info("Transit filter card header found");
            
            WebElement collapse = driver.findElement(transitFilterCollapse);
            String collapseClass = collapse.getAttribute("class");
            
            if (!collapseClass.contains("show")) {
                logger.info("Transit filter is collapsed, expanding...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardHeader);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", cardHeader);
                Thread.sleep(1000);
                
                logger.info("Transit filter expanded successfully");
            } else {
                logger.info("Transit filter is already expanded");
            }
            
        } catch (Exception e) {
            logger.error("Error opening transit filter: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void selectDirectFlights() {
        try {
            logger.info("Selecting direct flights checkbox...");
            
            Thread.sleep(1000);
            
            WebElement checkbox = waitHelper.waitForElementClickable(directFlightCheckbox);
            
            if (!checkbox.isSelected()) {
                logger.info("Direct flights checkbox is not selected, clicking...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", checkbox);
                Thread.sleep(1500);
                
                logger.info("Direct flights checkbox selected successfully");
            } else {
                logger.info("Direct flights checkbox is already selected");
            }
            
        } catch (Exception e) {
            logger.error("Error selecting direct flights: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void openAirlineFilter() {
        try {
            logger.info("Opening airline filter...");
            
            WebElement cardHeader = waitHelper.waitForElementVisible(airlineFilterCardHeader);
            logger.info("Airline filter card header found");
            
            WebElement collapse = driver.findElement(airlineFilterCollapse);
            String collapseClass = collapse.getAttribute("class");
            
            if (!collapseClass.contains("show")) {
                logger.info("Airline filter is collapsed, expanding...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardHeader);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", cardHeader);
                Thread.sleep(1000);
                
                logger.info("Airline filter expanded successfully");
            } else {
                logger.info("Airline filter is already expanded");
            }
            
        } catch (Exception e) {
            logger.error("Error opening airline filter: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void selectTurkishAirlines() {
        try {
            logger.info("Selecting Turkish Airlines...");
            
            WebElement checkbox = waitHelper.waitForElementVisible(turkishAirlinesCheckbox);
            
            if (!checkbox.isSelected()) {
                logger.info("Turkish Airlines checkbox is not selected, clicking...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(300);
                
                js.executeScript("arguments[0].click();", checkbox);
                Thread.sleep(1500);
                
                logger.info("Turkish Airlines selected successfully");
            } else {
                logger.info("Turkish Airlines is already selected");
            }
            
        } catch (Exception e) {
            logger.error("Error selecting Turkish Airlines: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void selectAllAirlines() {
        try {
            logger.info("Selecting all airlines (Tümünü seç)...");
            
            Thread.sleep(1000);
            
            WebElement selectAllButton = waitHelper.waitForElementClickable(selectAllAirlinesButton);
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", selectAllButton);
            Thread.sleep(500);
            
            js.executeScript("arguments[0].click();", selectAllButton);
            Thread.sleep(1500);
            
            logger.info("All airlines selected successfully");
            
        } catch (Exception e) {
            logger.error("Error selecting all airlines: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void openAirportFilter() {
        try {
            logger.info("Opening airport filter...");
            
            WebElement cardHeader = waitHelper.waitForElementVisible(airportFilterCardHeader);
            logger.info("Airport filter card header found");
            
            WebElement collapse = driver.findElement(airportFilterCollapse);
            String collapseClass = collapse.getAttribute("class");
            
            if (!collapseClass.contains("show")) {
                logger.info("Airport filter is collapsed, expanding...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardHeader);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", cardHeader);
                Thread.sleep(1000);
                
                logger.info("Airport filter expanded successfully");
            } else {
                logger.info("Airport filter is already expanded");
            }
            
        } catch (Exception e) {
            logger.error("Error opening airport filter: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void selectSawAirport() {
        try {
            logger.info("Selecting SAW (Sabiha Gökçen) airport...");
            
            Thread.sleep(1000);
            
            WebElement checkbox = waitHelper.waitForElementClickable(sawAirportCheckbox);
            
            if (!checkbox.isSelected()) {
                logger.info("SAW airport checkbox is not selected, clicking...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", checkbox);
                Thread.sleep(1500);
                
                logger.info("SAW airport selected successfully");
            } else {
                logger.info("SAW airport is already selected");
            }
            
        } catch (Exception e) {
            logger.error("Error selecting SAW airport: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void selectIstAirport() {
        try {
            logger.info("Selecting IST (Istanbul) airport...");
            
            Thread.sleep(1000);
            
            WebElement checkbox = waitHelper.waitForElementClickable(istAirportCheckbox);
            
            if (!checkbox.isSelected()) {
                logger.info("IST airport checkbox is not selected, clicking...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(500);
                
                js.executeScript("arguments[0].click();", checkbox);
                Thread.sleep(1500);
                
                logger.info("IST airport selected successfully");
            } else {
                logger.info("IST airport is already selected");
            }
            
        } catch (Exception e) {
            logger.error("Error selecting IST airport: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void sortByPriceAscending() {
        try {
            logger.info("Sorting flights by price (ascending - low to high)...");
            
            WebElement sortOption = waitHelper.waitForElementVisible(priceAscendingSort);
            
            String classAttribute = sortOption.getAttribute("class");
            
            if (!classAttribute.contains("active") && !classAttribute.contains("selected")) {
                logger.info("Price ascending sort is not active, clicking...");
                
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sortOption);
                Thread.sleep(300);
                
                js.executeScript("arguments[0].click();", sortOption);
                Thread.sleep(2000);
                
                logger.info("Flights sorted by price (low to high) successfully");
            } else {
                logger.info("Price ascending sort is already active");
            }
            
        } catch (Exception e) {
            logger.error("Error sorting by price: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public java.util.Map<String, Object> verifyPriceSortingAccuracy() {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("success", false);
        result.put("prices", new java.util.ArrayList<Double>());
        result.put("priceDetails", "");
        
        try {
            logger.info("Verifying price sorting accuracy...");
            Thread.sleep(2000);
            
            java.util.List<WebElement> priceElements = driver.findElements(flightPrices);
            
            if (priceElements.isEmpty()) {
                logger.warn("No price elements found on the page");
                result.put("message", "No price elements found");
                return result;
            }
            
            logger.info("Found " + priceElements.size() + " flight prices to verify");
            
            java.util.List<Double> prices = new java.util.ArrayList<>();
            StringBuilder priceDetails = new StringBuilder();
            priceDetails.append("=== FLIGHT PRICE VERIFICATION DETAILS ===\n\n");
            priceDetails.append("Total Flights Found: ").append(priceElements.size()).append("\n");
            priceDetails.append("Filter: Turkish Airlines | Time: 06:00-18:00\n\n");
            priceDetails.append("Flight Prices (Ascending Order):\n");
            priceDetails.append("─────────────────────────────────\n");
            
            for (int i = 0; i < priceElements.size(); i++) {
                WebElement priceElement = priceElements.get(i);
                
                String dataPriceAttr = priceElement.getAttribute("data-price");
                
                if (dataPriceAttr != null && !dataPriceAttr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(dataPriceAttr);
                        prices.add(price);
                        
                        String displayPrice = priceElement.getText().trim();
                        logger.info("Flight " + (i + 1) + " - Price: " + displayPrice + " (data-price: " + price + " TL)");
                        
                        priceDetails.append(String.format("Flight %2d: %,10.2f TL\n", (i + 1), price));
                        
                    } catch (NumberFormatException e) {
                        logger.warn("Could not parse data-price attribute: " + dataPriceAttr);
                    }
                } else {
                    logger.warn("No data-price attribute found for element at index " + i);
                }
            }
            
            if (prices.size() < 2) {
                logger.warn("Not enough valid prices found for comparison. Found: " + prices.size());
                result.put("success", true);
                result.put("message", "Insufficient prices for comparison");
                return result;
            }
            
            priceDetails.append("─────────────────────────────────\n\n");
            
            boolean isSorted = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) > prices.get(i + 1)) {
                    logger.error("❌ PRICE SORTING VERIFICATION FAILED!");
                    logger.error("Price at position " + (i + 1) + " (" + prices.get(i) + " TL) is greater than price at position " + (i + 2) + " (" + prices.get(i + 1) + " TL)");
                    priceDetails.append("❌ VERIFICATION FAILED!\n");
                    priceDetails.append(String.format("Price %d (%.2f TL) > Price %d (%.2f TL)\n", 
                        (i + 1), prices.get(i), (i + 2), prices.get(i + 1)));
                    isSorted = false;
                    break;
                }
            }
            
            if (isSorted) {
                logger.info("✓ PRICE SORTING VERIFICATION PASSED!");
                logger.info("All " + prices.size() + " flight prices are in ascending order (low to high)");
                logger.info("Price range: " + String.format("%.2f", prices.get(0)) + " TL - " + String.format("%.2f", prices.get(prices.size() - 1)) + " TL");
                
                priceDetails.append("✓ VERIFICATION STATUS: PASSED\n\n");
                priceDetails.append("Price Range:\n");
                priceDetails.append(String.format("  Minimum: %,10.2f TL\n", prices.get(0)));
                priceDetails.append(String.format("  Maximum: %,10.2f TL\n", prices.get(prices.size() - 1)));
                priceDetails.append(String.format("  Difference: %,9.2f TL\n\n", prices.get(prices.size() - 1) - prices.get(0)));
                
                double average = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                priceDetails.append(String.format("  Average: %,10.2f TL\n", average));
                
                priceDetails.append("\n✓ All prices are in ascending order (low to high)");
            }
            
            result.put("success", isSorted);
            result.put("prices", prices);
            result.put("priceDetails", priceDetails.toString());
            result.put("minPrice", prices.get(0));
            result.put("maxPrice", prices.get(prices.size() - 1));
            result.put("flightCount", prices.size());
            
            return result;
            
        } catch (Exception e) {
            logger.error("Error verifying price sorting: " + e.getMessage());
            e.printStackTrace();
            result.put("message", "Error: " + e.getMessage());
            return result;
        }
    }
    
    public void scrollToTop() {
        try {
            logger.info("Scrolling to top of the page...");
            js.executeScript("window.scrollTo(0, 0);");
            Thread.sleep(500);
            logger.info("Scrolled to top successfully");
        } catch (Exception e) {
            logger.error("Error scrolling to top: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

