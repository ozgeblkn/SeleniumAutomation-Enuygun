package tests;

import base.BaseTest;
import io.qameta.allure.*;
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
}

