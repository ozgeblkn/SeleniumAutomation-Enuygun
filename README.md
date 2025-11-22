# Selenium Test Automation Framework - Enuygun.com

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.25.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![Allure](https://img.shields.io/badge/Allure-2.21.0-yellow.svg)](https://allurereport.org/)

A comprehensive Selenium WebDriver test automation framework built with Java, TestNG, and Allure Reports for testing [Enuygun.com](https://www.enuygun.com) flight search functionality.

## 📋 Table of Contents

- [About The Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Cases](#test-cases)
- [Allure Reports](#allure-reports)
- [Logging](#logging)
- [Screenshots](#screenshots)
- [Best Practices](#best-practices)
- [Author](#author)

## 🎯 About The Project

This project is a robust Selenium test automation framework designed to test flight search functionality on Enuygun.com. It follows industry best practices including Page Object Model (POM) design pattern, OOP principles, and comprehensive reporting with Allure.

### Key Highlights

- **Page Object Model (POM)** architecture for maintainable test code
- **Multi-browser support** (Chrome and Firefox)
- **Parameterized test data** from configuration files
- **Detailed Allure Reports** with test steps, descriptions, and screenshots
- **Automatic screenshot capture** on test failures
- **Comprehensive logging** with Log4j2
- **Smart wait mechanisms** with explicit waits
- **Clean code principles** following OOP best practices

## 🛠 Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming Language |
| Selenium WebDriver | 4.25.0 | Browser Automation |
| TestNG | 7.10.2 | Test Framework |
| Allure Reports | 2.21.0 | Test Reporting |
| WebDriverManager | 5.8.0 | Browser Driver Management |
| Log4j2 | 2.22.0 | Logging Framework |
| Maven | 3.6+ | Build Tool & Dependency Management |
| AspectJ | 1.9.22 | AOP for Allure Integration |

## 📁 Project Structure

```
ozgebalkan-selenium/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base/
│   │   │   │   └── BasePage.java              # Base class for all Page Objects
│   │   │   ├── pages/
│   │   │   │   ├── HomePage.java              # Home page interactions
│   │   │   │   └── FlightListPage.java        # Flight listing page interactions
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java          # Configuration file reader
│   │   │       ├── DriverManager.java         # WebDriver lifecycle management
│   │   │       ├── WaitHelper.java            # Explicit wait utilities
│   │   │       └── ScreenshotUtil.java        # Screenshot capture utility
│   │   └── resources/
│   │       ├── config.properties              # Test configuration parameters
│   │       └── log4j2.xml                     # Logging configuration
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java              # Base class for all test classes
│       │   ├── tests/
│       │   │   └── FlightSearchTest.java      # Flight search test cases
│       │   └── listeners/
│       │       └── TestListener.java          # TestNG listener for reporting
│       └── resources/
│           ├── testng.xml                     # TestNG suite configuration
│           └── test-data.properties           # Additional test data
│
├── target/
│   ├── allure-results/                        # Allure test results
│   ├── screenshots/                           # Test failure screenshots
│   └── surefire-reports/                      # TestNG reports
│
├── logs/                                      # Application logs
├── .gitignore                                 # Git ignore rules
├── pom.xml                                    # Maven configuration
└── README.md                                  # Project documentation
```

## ✨ Features

### Design Patterns & Architecture
- ✅ **Page Object Model (POM)** - Separation of test logic and page interactions
- ✅ **Object-Oriented Programming (OOP)** - Encapsulation, Inheritance, Abstraction
- ✅ **Single Responsibility Principle** - Each class has a single, well-defined purpose
- ✅ **DRY Principle** - Code reusability through base classes and utilities

### Test Framework Capabilities
- ✅ **Multi-Browser Support** - Chrome and Firefox with easy configuration
- ✅ **Headless Mode** - Run tests without GUI for CI/CD pipelines
- ✅ **Parameterized Tests** - Test data externalized in `config.properties`
- ✅ **Explicit Waits** - Smart waiting mechanisms for dynamic elements
- ✅ **Screenshot on Failure** - Automatic capture and attachment to reports
- ✅ **Detailed Logging** - Console and file logging with Log4j2
- ✅ **Allure Reports** - Beautiful, interactive HTML reports with test steps
- ✅ **TestNG Listeners** - Custom listeners for enhanced reporting
- ✅ **Error Handling** - Robust exception handling and recovery

### Technical Features
- ✅ **WebDriverManager** - Automatic browser driver management
- ✅ **ThreadLocal WebDriver** - Support for parallel test execution
- ✅ **UTF-8 Encoding** - Proper handling of Turkish characters
- ✅ **JavaScript Executor** - Advanced browser interactions
- ✅ **Actions Class** - Realistic user interactions (drag-and-drop, sliders)
- ✅ **Multiple Window/Tab Handling** - Seamless navigation between browser tabs
- ✅ **Dynamic Element Handling** - Robust locator strategies with `data-testid`

## 📋 Prerequisites

Before running the tests, ensure you have the following installed:

- **Java Development Kit (JDK)** - Version 17 or higher
  - [Download JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - Verify: `java -version`

- **Apache Maven** - Version 3.6 or higher
  - [Download Maven](https://maven.apache.org/download.cgi)
  - Verify: `mvn -version`

- **Web Browser**
  - Chrome (latest version) or
  - Firefox (latest version)

- **Git** (Optional, for cloning the repository)
  - [Download Git](https://git-scm.com/downloads)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/ozgeblkn/SeleniumAutomation-Enuygun.git
cd SeleniumAutomation-Enuygun
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required Maven dependencies
- Compile the project
- Run the tests
- Generate test reports

### 3. Verify Installation

```bash
mvn clean compile
```

If successful, you should see `BUILD SUCCESS` in the console.

## ⚙️ Configuration

### Test Configuration (`src/main/resources/config.properties`)

```properties
# Browser Configuration
browser=chrome                          # Options: chrome, firefox
headless=false                          # Options: true, false
timeout=10                              # Explicit wait timeout in seconds

# Application Configuration
baseUrl=https://www.enuygun.com        # Application URL

# Test Data - Flight Search Parameters
originCity=İstanbul                     # Departure city
destinationCity=Ankara                  # Arrival city
departureDate=25.11.2025               # Departure date (DD.MM.YYYY)
returnDate=22.12.2025                  # Return date (DD.MM.YYYY)

# Reporting
screenshotPath=test-output/screenshots/ # Screenshot save location
```

### Browser Configuration Options

| Option | Description |
|--------|-------------|
| `chrome` | Run tests on Google Chrome (default) |
| `firefox` | Run tests on Mozilla Firefox |

### Headless Mode

| Value | Description |
|-------|-------------|
| `false` | Run browser with visible GUI (default) |
| `true` | Run browser in headless mode (no GUI) |

### Logging Configuration (`src/main/resources/log4j2.xml`)

Logs are configured to output to both console and file (`logs/test-automation.log`).

## 🧪 Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Tests with Specific Browser

**Chrome:**
```bash
mvn clean test -Dbrowser=chrome
```

**Firefox:**
```bash
mvn clean test -Dbrowser=firefox
```

### Run Tests in Headless Mode

```bash
mvn clean test -Dheadless=true
```

### Run Specific Test Suite

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Run and Generate Allure Report

```bash
mvn clean test && mvn allure:serve
```

## 📝 Test Cases

### Test Case 1: Basic Flight Search with Time Filter

**Objective:** Verify flight search functionality with departure time filtering

**Test Steps:**
1. Navigate to www.enuygun.com
2. Disable 'Ucuz bilet bul' checkbox if checked
3. Enter origin city: Istanbul (parameterized)
4. Select first option from origin dropdown
5. Enter destination city: Ankara (parameterized)
6. Select first option from destination dropdown
7. Select departure date (parameterized from config)
8. Select return date (parameterized from config)
9. Click search button
10. Wait for flight results page to load
11. Accept cookie consent if present
12. Open time filter section
13. Set departure time filter: 06:00 - 18:00
14. Verify filtered results

**Expected Results:**
- ✅ Flight search completes successfully
- ✅ Time filter is applied correctly
- ✅ Only flights between 06:00-18:00 are displayed
- ✅ All displayed flights match the selected route

**Parameters:**
- Origin City: İstanbul
- Destination City: Ankara
- Departure Date: 25.11.2025
- Return Date: 22.12.2025
- Time Filter: 06:00 - 18:00

## 📊 Allure Reports

### Generate and View Allure Report

**Option 1: Serve Report (Recommended)**
```bash
mvn allure:serve
```
This command will:
- Generate the Allure report
- Start a local web server
- Automatically open the report in your default browser

**Option 2: Generate Report to Directory**
```bash
mvn allure:report
```
Report will be generated in: `target/site/allure-maven-plugin/`

### Allure Report Features

- ✅ **Test Suites Overview** - Summary of all test executions
- ✅ **Detailed Test Steps** - Step-by-step test execution flow
- ✅ **Test Parameters** - All parameterized test data
- ✅ **Test Duration** - Execution time for each test
- ✅ **Failure Screenshots** - Automatic screenshot attachment
- ✅ **Logs** - Detailed execution logs
- ✅ **Categories** - Test categorization by features/stories
- ✅ **Trends** - Historical test execution trends
- ✅ **Severity Levels** - Test prioritization

### Allure Report Structure

```
📊 Allure Report
├── 📈 Overview
│   ├── Test execution summary
│   ├── Pass/Fail statistics
│   └── Duration charts
├── 📋 Suites
│   └── Test suite breakdown
├── 🎯 Behaviors
│   ├── Features
│   └── Stories
├── 📦 Packages
│   └── Test organization by packages
├── 📊 Graphs
│   ├── Status chart
│   ├── Severity chart
│   └── Duration chart
└── ⏱️ Timeline
    └── Test execution timeline
```

## 📄 Logging

### Log Configuration

Logs are configured with **Log4j2** and output to:
- **Console** - Real-time test execution logs
- **File** - `logs/test-automation.log` (persistent logs)

### Log Levels

| Level | Description | Usage |
|-------|-------------|-------|
| `INFO` | Informational messages | Test steps, navigation |
| `WARN` | Warning messages | Expected exceptions, fallbacks |
| `ERROR` | Error messages | Test failures, unexpected issues |
| `DEBUG` | Detailed debugging | Element interactions (disabled by default) |

### Sample Log Output

```
2025-11-22 15:47:49 INFO  FlightSearchTest - Test Parameters - Origin: İstanbul, Destination: Ankara
2025-11-22 15:47:50 INFO  HomePage - Clicked on origin input to open autosuggestion
2025-11-22 15:47:51 INFO  HomePage - Entered origin city: İstanbul
2025-11-22 15:47:52 INFO  HomePage - Selected first option from origin dropdown
2025-11-22 15:47:54 INFO  FlightSearchTest - Step 1 completed: Origin city entered successfully
```

## 📸 Screenshots

### Automatic Screenshot Capture

Screenshots are automatically captured in the following scenarios:
- ❌ **Test Failure** - Captured at the point of failure
- 🔍 **Assertion Failure** - Visual evidence of expected vs actual state

### Screenshot Location

```
test-output/
└── screenshots/
    ├── testFlightSearchStep1_2025-11-22_15-48-41.png
    └── ...
```

### Screenshot Features
- ✅ **Automatic Naming** - Test name + timestamp
- ✅ **Full Page Screenshots** - Complete page capture
- ✅ **Allure Integration** - Automatically attached to reports
- ✅ **PNG Format** - High quality, compressed images

## 🎯 Best Practices Implemented

### Code Quality
- ✅ **Clean Code** - Readable, maintainable, and self-documenting
- ✅ **Meaningful Names** - Descriptive variable, method, and class names
- ✅ **Single Responsibility** - Each class/method has one responsibility
- ✅ **DRY (Don't Repeat Yourself)** - Code reusability through inheritance
- ✅ **SOLID Principles** - Object-oriented design principles

### Test Automation
- ✅ **Stable Locators** - Using `data-testid` for reliability
- ✅ **Explicit Waits** - No hard-coded sleeps (except where necessary)
- ✅ **Independent Tests** - Tests can run independently and in any order
- ✅ **Parameterization** - Test data externalized and configurable
- ✅ **Error Handling** - Graceful handling of exceptions

### Framework Design
- ✅ **Page Object Model** - Separation of concerns
- ✅ **Base Classes** - Common functionality in base classes
- ✅ **Utility Classes** - Reusable helper methods
- ✅ **Configuration Management** - Centralized configuration
- ✅ **Logging Strategy** - Comprehensive logging at all levels

## 🔍 Troubleshooting

### Common Issues

**Issue:** Tests fail with `ElementNotInteractableException`
- **Solution:** Check if elements are visible and enabled. Increase timeout in `config.properties`.

**Issue:** Turkish characters not displayed correctly
- **Solution:** Ensure UTF-8 encoding in `ConfigReader.java` (already implemented).

**Issue:** Browser driver not found
- **Solution:** WebDriverManager automatically downloads drivers. Check internet connection.

**Issue:** Allure report not generated
- **Solution:** Run `mvn clean test` first to generate results, then `mvn allure:serve`.

**Issue:** Screenshot not captured on failure
- **Solution:** Ensure `screenshotPath` directory exists and is writable.

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Contact

**Özge Balkan**

- GitHub: [@ozgeblkn](https://github.com/ozgeblkn)
- Email: ozgeblknnn@gmail.com
- Repository: [SeleniumAutomation-Enuygun](https://github.com/ozgeblkn/SeleniumAutomation-Enuygun)

## 📄 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- [Selenium WebDriver](https://www.selenium.dev/) - Browser automation
- [TestNG](https://testng.org/) - Testing framework
- [Allure Framework](https://allurereport.org/) - Reporting framework
- [Enuygun.com](https://www.enuygun.com/) - Application under test

---

⭐ **If you find this project helpful, please give it a star!** ⭐

**Last Updated:** November 22, 2025
