# Selenium Automation Framework

Java Selenium TestNG Allure automation framework for web testing.

## Technologies Used

- Java 17
- Selenium WebDriver 4.25.0
- TestNG 7.10.2
- Allure Reports 2.21.0
- WebDriverManager 5.8.0
- Log4j2 2.22.0
- Maven

## Project Structure

```
src/
├── main/java/
│   ├── base/
│   │   └── BasePage.java
│   ├── pages/
│   └── utils/
│       ├── ConfigReader.java
│       ├── DriverManager.java
│       ├── WaitHelper.java
│       └── ScreenshotUtil.java
├── test/java/
│   ├── base/
│   │   └── BaseTest.java
│   ├── tests/
│   └── listeners/
│       └── TestListener.java
└── resources/
    ├── config.properties
    ├── log4j2.xml
    └── testng.xml
```

## Prerequisites

- JDK 17 or higher
- Maven 3.6+
- Chrome or Firefox browser

## Installation

1. Clone the repository
```bash
git clone <repository-url>
cd ozgebalkan-selenium
```

2. Install dependencies
```bash
mvn clean install
```

## Configuration

Edit `src/main/resources/config.properties`:

```properties
browser=chrome
headless=false
timeout=10
baseUrl=https://www.saucedemo.com
screenshotPath=test-output/screenshots/
```

### Browser Options
- `chrome` - Run tests on Chrome
- `firefox` - Run tests on Firefox

### Headless Mode
- `true` - Run browser in headless mode
- `false` - Run browser with UI

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test suite
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run with specific browser
```bash
mvn clean test -Dbrowser=firefox
```

## Features

- **Page Object Model (POM)** - Organized page structure
- **Multi-browser Support** - Chrome and Firefox
- **Configurable** - Easy configuration via properties file
- **Screenshot on Failure** - Automatic screenshot capture
- **Allure Reports** - Beautiful HTML test reports
- **Logging** - Detailed logs with Log4j2
- **Explicit Waits** - Smart waiting mechanism
- **OOP Principles** - Clean and maintainable code

## Generate Allure Reports

```bash
mvn allure:serve
```

Or generate and view manually:
```bash
mvn allure:report
```

## Logging

Logs are saved in `logs/test-automation.log`

## Screenshots

Failed test screenshots are saved in `test-output/screenshots/`

## Author

Mustafa

## License

This project is open source.

