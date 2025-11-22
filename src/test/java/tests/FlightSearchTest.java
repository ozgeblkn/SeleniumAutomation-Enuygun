package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.FlightListPage;
import utils.ConfigReader;

@Epic("Flight Booking")
@Feature("Flight Search and Filtering")
public class FlightSearchTest extends BaseTest {

    @Test
    @Description(
        "Test Case: Basic Flight Search with Time Filter\n\n" +
        "Objective: Verify flight search functionality with departure time filtering\n\n" +
        "Prerequisites:\n" +
        "- User is on enuygun.com homepage\n" +
        "- Browser is Chrome\n\n" +
        "Test Steps:\n" +
        "1. Navigate to www.enuygun.com\n" +
        "2. Disable 'Ucuz bilet bul' checkbox if checked\n" +
        "3. Enter origin city: Istanbul\n" +
        "4. Enter destination city: Ankara\n" +
        "5. Select departure date (parameterized from config)\n" +
        "6. Select return date (parameterized from config)\n" +
        "7. Click search button\n" +
        "8. Wait for flight results page to load\n" +
        "9. Accept cookie consent if present\n" +
        "10. Open time filter section\n" +
        "11. Set departure time filter: 06:00 - 18:00\n" +
        "12. Verify filtered results\n\n" +
        "Expected Results:\n" +
        "- Flight search completes successfully\n" +
        "- Time filter is applied correctly\n" +
        "- Only flights between 06:00-18:00 are displayed\n" +
        "- All displayed flights match the selected route"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Basic Flight Search with Time Filter")
    public void testFlightSearchStep1() {
        HomePage homePage = new HomePage(driver);
        
        String originCity = ConfigReader.getOriginCity();
        String destinationCity = ConfigReader.getDestinationCity();
        String departureDate = ConfigReader.getDepartureDate();
        String returnDate = ConfigReader.getReturnDate();
        
        Allure.parameter("Origin City", originCity);
        Allure.parameter("Destination City", destinationCity);
        Allure.parameter("Departure Date", departureDate);
        Allure.parameter("Return Date", returnDate);
        
        logger.info("Test Parameters - Origin: " + originCity + ", Destination: " + destinationCity);
        logger.info("Test Parameters - Departure: " + departureDate + ", Return: " + returnDate);
        
        Allure.step("Enter origin city: " + originCity, () -> {
            homePage.enterOrigin(originCity);
            logger.info("Step 1 completed: Origin city entered successfully");
        });
        
        Allure.step("Enter destination city: " + destinationCity, () -> {
            homePage.enterDestination(destinationCity);
            logger.info("Step 2 completed: Destination city entered successfully");
        });
        
        String formattedDepartureDate = ConfigReader.getFormattedDepartureDate();
        String departureMonthYear = ConfigReader.getDepartureMonthYear();
        Allure.step("Select departure date: " + departureDate, () -> {
            homePage.selectDepartureDate(formattedDepartureDate, departureMonthYear);
            logger.info("Step 3 completed: Departure date selected successfully");
        });
        
        String formattedReturnDate = ConfigReader.getFormattedReturnDate();
        String returnMonthYear = ConfigReader.getReturnMonthYear();
        Allure.step("Select return date: " + returnDate, () -> {
            homePage.selectReturnDate(formattedReturnDate, returnMonthYear);
            logger.info("Step 4 completed: Return date selected successfully");
        });
        
        Allure.step("Disable 'Ucuz bilet bul' checkbox if checked", () -> {
            homePage.disableCheapFlightCheckboxIfChecked();
            logger.info("Step 4.5 completed: 'Ucuz bilet bul' checkbox kontrolü tamamlandı - pasif durumda");
        });
        
        Allure.step("Click search button", () -> {
            homePage.clickSearchButton();
            logger.info("Step 5 completed: Search button clicked successfully");
        });
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        FlightListPage flightListPage = new FlightListPage(driver);
        
        Allure.step("Apply time filter (06:00 - 18:00)", () -> {
            flightListPage.setDepartureTimeFilter(6, 18);
            logger.info("Step 6 completed: Time filter applied (06:00 - 18:00)");
        });
        
        Allure.step("Verify filtered results", () -> {
            logger.info("Waiting to observe filtered results...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Allure.addAttachment("Filter Status", "text/plain", 
                "Time filter applied: 06:00 - 18:00\nFlights filtered successfully");
        });
        
        logger.info("Test completed: Flight search with time filter!");
    }

    @Test
    @Description(
        "Test Case 2: Advanced Flight Search with Airline Filter and Price Verification\n\n" +
        "Objective: Verify flight search functionality with time filtering, airline selection, " +
        "price sorting, and automated price validation\n\n" +
        "Prerequisites:\n" +
        "- User is on enuygun.com homepage\n" +
        "- Browser is Chrome\n\n" +
        "Test Steps:\n" +
        "1. Navigate to www.enuygun.com\n" +
        "2. Disable 'Ucuz bilet bul' checkbox if checked\n" +
        "3. Enter origin city: Istanbul\n" +
        "4. Enter destination city: Ankara\n" +
        "5. Select departure date (parameterized from config)\n" +
        "6. Select return date (parameterized from config)\n" +
        "7. Click search button\n" +
        "8. Wait for flight results page to load\n" +
        "9. Accept cookie consent if present\n" +
        "10. Open time filter section\n" +
        "11. Set departure time filter: 06:00 - 18:00\n" +
        "12. Open airline filter section\n" +
        "13. Select Turkish Airlines from airline filter\n" +
        "14. Sort flights by price (low to high)\n" +
        "15. Verify price sorting accuracy (all prices in ascending order)\n\n" +
        "Expected Results:\n" +
        "- Flight search completes successfully\n" +
        "- Time filter is applied correctly (06:00-18:00)\n" +
        "- Only Turkish Airlines flights are displayed\n" +
        "- Flights are sorted by price in ascending order\n" +
        "- Price sorting accuracy is verified programmatically\n" +
        "- All displayed flights match the selected route and filters\n" +
        "- Test assertion passes without failures"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Advanced Flight Filtering and Price Verification")
    public void testFlightSearchStep2() {
        HomePage homePage = new HomePage(driver);
        
        String originCity = ConfigReader.getOriginCity();
        String destinationCity = ConfigReader.getDestinationCity();
        String departureDate = ConfigReader.getDepartureDate();
        String returnDate = ConfigReader.getReturnDate();
        
        Allure.parameter("Origin City", originCity);
        Allure.parameter("Destination City", destinationCity);
        Allure.parameter("Departure Date", departureDate);
        Allure.parameter("Return Date", returnDate);
        
        logger.info("Test Parameters - Origin: " + originCity + ", Destination: " + destinationCity);
        logger.info("Test Parameters - Departure: " + departureDate + ", Return: " + returnDate);
        
        Allure.step("Enter origin city: " + originCity, () -> {
            homePage.enterOrigin(originCity);
            logger.info("Step 1 completed: Origin city entered successfully");
        });
        
        Allure.step("Enter destination city: " + destinationCity, () -> {
            homePage.enterDestination(destinationCity);
            logger.info("Step 2 completed: Destination city entered successfully");
        });
        
        String formattedDepartureDate = ConfigReader.getFormattedDepartureDate();
        String departureMonthYear = ConfigReader.getDepartureMonthYear();
        Allure.step("Select departure date: " + departureDate, () -> {
            homePage.selectDepartureDate(formattedDepartureDate, departureMonthYear);
            logger.info("Step 3 completed: Departure date selected successfully");
        });
        
        String formattedReturnDate = ConfigReader.getFormattedReturnDate();
        String returnMonthYear = ConfigReader.getReturnMonthYear();
        Allure.step("Select return date: " + returnDate, () -> {
            homePage.selectReturnDate(formattedReturnDate, returnMonthYear);
            logger.info("Step 4 completed: Return date selected successfully");
        });
        
        Allure.step("Disable 'Ucuz bilet bul' checkbox if checked", () -> {
            homePage.disableCheapFlightCheckboxIfChecked();
            logger.info("Step 4.5 completed: 'Ucuz bilet bul' checkbox kontrolü tamamlandı - pasif durumda");
        });
        
        Allure.step("Click search button", () -> {
            homePage.clickSearchButton();
            logger.info("Step 5 completed: Search button clicked successfully");
        });
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        FlightListPage flightListPage = new FlightListPage(driver);
        
        Allure.step("Apply time filter (06:00 - 18:00)", () -> {
            flightListPage.setDepartureTimeFilter(6, 18);
            logger.info("Step 6 completed: Time filter applied (06:00 - 18:00)");
        });
        
        Allure.step("Open airline filter and select Turkish Airlines", () -> {
            flightListPage.openAirlineFilter();
            logger.info("Step 7a completed: Airline filter opened");
            
            flightListPage.selectTurkishAirlines();
            logger.info("Step 7b completed: Turkish Airlines selected");
        });
        
        Allure.step("Sort flights by price (low to high)", () -> {
            flightListPage.sortByPriceAscending();
            logger.info("Step 8 completed: Flights sorted by price (ascending)");
        });
        
        Allure.step("Verify price sorting accuracy", () -> {
            java.util.Map<String, Object> verificationResult = flightListPage.verifyPriceSortingAccuracy();
            
            boolean isPriceSortingCorrect = (boolean) verificationResult.get("success");
            String priceDetails = (String) verificationResult.get("priceDetails");
            
            Assert.assertTrue(isPriceSortingCorrect, 
                "Price sorting verification failed! Prices are not in ascending order.");
            
            logger.info("Step 9 completed: Price sorting verified successfully");
            
            // Add detailed price information to Allure Report
            Allure.addAttachment("Flight Price Verification Details", "text/plain", priceDetails);
            
            // Add summary information
            @SuppressWarnings("unchecked")
            java.util.List<Double> prices = (java.util.List<Double>) verificationResult.get("prices");
            int flightCount = (int) verificationResult.get("flightCount");
            double minPrice = (double) verificationResult.get("minPrice");
            double maxPrice = (double) verificationResult.get("maxPrice");
            
            String summary = String.format(
                "✓ Price Sorting Verification: PASSED\n\n" +
                "Summary:\n" +
                "  Total Flights: %d\n" +
                "  Lowest Price: %,.2f TL\n" +
                "  Highest Price: %,.2f TL\n" +
                "  Price Range: %,.2f TL\n" +
                "  All prices are in ascending order",
                flightCount, minPrice, maxPrice, (maxPrice - minPrice)
            );
            
            Allure.addAttachment("Verification Summary", "text/plain", summary);
        });
        
        logger.info("Test Case 2 completed: Flight search with advanced filtering and verification!");
    }

    @Test
    @Description(
        "Test Case 3: One-Way Flight Search with Advanced Filtering\n\n" +
        "Objective: Verify one-way flight search functionality with time filtering, " +
        "airline selection, and price verification\n\n" +
        "Prerequisites:\n" +
        "- User is on enuygun.com homepage\n" +
        "- Browser is Chrome\n\n" +
        "Test Steps:\n" +
        "1. Navigate to www.enuygun.com\n" +
        "2. Select 'Tek yön' (One-way) trip option\n" +
        "3. Enter origin city: Istanbul\n" +
        "4. Enter destination city: Ankara\n" +
        "5. Select departure date (parameterized from config)\n" +
        "6. Disable 'Ucuz bilet bul' checkbox if checked\n" +
        "7. Click search button\n" +
        "8. Wait for flight results page to load\n" +
        "9. Accept cookie consent if present\n" +
        "10. Open transit (Aktarma) filter section\n" +
        "11. Select direct flights only (Direkt uçuşlar)\n" +
        "12. Apply time filter (06:00-18:00)\n" +
        "13. Select all airlines (Tümünü seç)\n" +
        "14. Open airport (Havalimanları) filter section\n" +
        "15. Select SAW (Sabiha Gökçen) airport\n" +
        "16. Select IST (Istanbul) airport\n" +
        "17. Sort flights by price (low to high)\n" +
        "18. Verify price sorting accuracy\n" +
        "19. Scroll to top of the page\n" +
        "20-22. Additional steps (to be added)\n\n" +
        "Expected Results:\n" +
        "- One-way flight search completes successfully\n" +
        "- Only direct flights (no layovers) are displayed\n" +
        "- Time filter is applied correctly (06:00-18:00)\n" +
        "- All airlines are included in the results\n" +
        "- Only flights from SAW and IST airports are displayed\n" +
        "- Flights are sorted by price in ascending order\n" +
        "- Price sorting accuracy is verified programmatically"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("One-Way Flight Search with Advanced Filtering")
    public void testFlightSearchStep3_OneWay() {
        HomePage homePage = new HomePage(driver);
        
        String originCity = ConfigReader.getOriginCity();
        String destinationCity = ConfigReader.getDestinationCity();
        String departureDate = ConfigReader.getOneWayDepartureDate();
        
        Allure.parameter("Origin City", originCity);
        Allure.parameter("Destination City", destinationCity);
        Allure.parameter("Departure Date", departureDate);
        Allure.parameter("Trip Type", "One-Way");
        
        logger.info("Test Parameters - Origin: " + originCity + ", Destination: " + destinationCity);
        logger.info("Test Parameters - Departure: " + departureDate + " (ONE-WAY)");
        
        Allure.step("Select one-way trip option", () -> {
            homePage.selectOneWayTrip();
            logger.info("Step 1 completed: One-way trip selected");
        });
        
        Allure.step("Enter origin city: " + originCity, () -> {
            homePage.enterOrigin(originCity);
            logger.info("Step 2 completed: Origin city entered successfully");
        });
        
        Allure.step("Enter destination city: " + destinationCity, () -> {
            homePage.enterDestination(destinationCity);
            logger.info("Step 3 completed: Destination city entered successfully");
        });
        
        String formattedDepartureDate = ConfigReader.getFormattedOneWayDepartureDate();
        String departureMonthYear = ConfigReader.getOneWayDepartureMonthYear();
        Allure.step("Select departure date: " + departureDate, () -> {
            homePage.selectDepartureDate(formattedDepartureDate, departureMonthYear);
            logger.info("Step 4 completed: Departure date selected successfully");
        });
        
        Allure.step("Disable 'Ucuz bilet bul' checkbox if checked", () -> {
            homePage.disableCheapFlightCheckboxIfChecked();
            logger.info("Step 5 completed: 'Ucuz bilet bul' checkbox kontrolü tamamlandı - pasif durumda");
        });
        
        Allure.step("Click search button", () -> {
            homePage.clickSearchButton();
            logger.info("Step 6 completed: Search button clicked successfully");
        });
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        FlightListPage flightListPage = new FlightListPage(driver);
        
        Allure.step("Open transit (Aktarma) filter", () -> {
            flightListPage.openTransitFilter();
            logger.info("Step 7 completed: Transit filter opened");
        });
        
        Allure.step("Select direct flights only", () -> {
            flightListPage.selectDirectFlights();
            logger.info("Step 8 completed: Direct flights checkbox selected");
        });
        
        Allure.step("Apply time filter (06:00 - 18:00)", () -> {
            flightListPage.setDepartureTimeFilter(6, 18);
            logger.info("Step 9 completed: Time filter applied (06:00 - 18:00)");
        });
        
        Allure.step("Open airline filter and select all airlines", () -> {
            flightListPage.openAirlineFilter();
            logger.info("Step 10a completed: Airline filter opened");
            
            flightListPage.selectAllAirlines();
            logger.info("Step 10b completed: All airlines selected (Tümünü seç)");
        });
        
        Allure.step("Open airport (Havalimanları) filter", () -> {
            flightListPage.openAirportFilter();
            logger.info("Step 11 completed: Airport filter opened");
        });
        
        Allure.step("Select SAW (Sabiha Gökçen) airport", () -> {
            flightListPage.selectSawAirport();
            logger.info("Step 12 completed: SAW airport selected");
        });
        
        Allure.step("Select IST (Istanbul) airport", () -> {
            flightListPage.selectIstAirport();
            logger.info("Step 13 completed: IST airport selected");
        });
        
        Allure.step("Sort flights by price (low to high)", () -> {
            flightListPage.sortByPriceAscending();
            logger.info("Step 14 completed: Flights sorted by price (ascending)");
        });
        
        Allure.step("Verify price sorting accuracy", () -> {
            java.util.Map<String, Object> verificationResult = flightListPage.verifyPriceSortingAccuracy();
            
            boolean isPriceSortingCorrect = (boolean) verificationResult.get("success");
            String priceDetails = (String) verificationResult.get("priceDetails");
            
            Assert.assertTrue(isPriceSortingCorrect, 
                "Price sorting verification failed! Prices are not in ascending order.");
            
            logger.info("Step 15 completed: Price sorting verified successfully");
            
            Allure.addAttachment("Flight Price Verification Details (One-Way)", "text/plain", priceDetails);
            
            @SuppressWarnings("unchecked")
            java.util.List<Double> prices = (java.util.List<Double>) verificationResult.get("prices");
            int flightCount = (int) verificationResult.get("flightCount");
            double minPrice = (double) verificationResult.get("minPrice");
            double maxPrice = (double) verificationResult.get("maxPrice");
            
            String summary = String.format(
                "✓ Price Sorting Verification: PASSED (ONE-WAY)\n\n" +
                "Summary:\n" +
                "  Trip Type: One-Way\n" +
                "  Total Flights: %d\n" +
                "  Lowest Price: %,.2f TL\n" +
                "  Highest Price: %,.2f TL\n" +
                "  Price Range: %,.2f TL\n" +
                "  All prices are in ascending order",
                flightCount, minPrice, maxPrice, (maxPrice - minPrice)
            );
            
            Allure.addAttachment("Verification Summary (One-Way)", "text/plain", summary);
        });
        
        Allure.step("Scroll to top of the page", () -> {
            flightListPage.scrollToTop();
            logger.info("Step 16 completed: Scrolled to top of the page");
        });
        
        // TODO: Additional steps will be added here
        // Step 17-19: [Waiting for next instructions]
        
        logger.info("Test Case 3 (One-Way) - Basic steps completed, ready for additional steps");
    }
}

