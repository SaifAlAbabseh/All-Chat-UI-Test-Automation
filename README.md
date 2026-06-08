# All-Chat UI Test Automation Framework

Welcome to the **All-Chat UI Test Automation Framework**! This framework is designed to provide robust, maintainable, and scalable automated UI testing for the All-Chat application. It leverages Selenium WebDriver, Cucumber BDD, and TestNG, built with Java and Maven.

## 🌟 Key Features

*   **Behavior-Driven Development (BDD):** Uses Cucumber to write test scenarios in plain English (Gherkin syntax), bridging the gap between technical and non-technical stakeholders.
*   **Page Object Model (POM):** Implements the POM design pattern to enhance test maintenance and reduce code duplication.
*   **Cross-Browser Testing:** Supports testing across different browsers via dynamic parameters.
*   **Headless & Mobile View Testing:** Configurable to run tests in headless mode or simulate mobile resolutions.
*   **CI/CD Integration:** Ready for Jenkins integration with a provided `Jenkinsfile`, including automated video recording of test runs (via Xvfb and ffmpeg) and Slack notifications.
*   **Detailed Reporting:** Generates comprehensive Cucumber HTML and JSON reports.

## 🏗️ Framework Structure

The framework is organized into the following directory structure:

```text
├── src/
│   ├── main/java/
│   │   ├── helpers/       # Helper classes for common UI interactions (e.g., waits, interactions)
│   │   ├── pages/         # Page Object Model (POM) classes representing web pages
│   │   └── utils/         # Utilities like DriverManager, Configuration readers, etc.
│   └── test/
│       ├── java/
│       │   ├── hooks/             # Cucumber hooks (e.g., @Before, @After) for setup and teardown
│       │   ├── runners/           # Cucumber TestNG runners to execute the feature files
│       │   └── stepdefinitions/   # Java code that implements the steps defined in the feature files
│       └── resources/
│           └── features/          # Cucumber .feature files containing Gherkin scenarios
├── suites/                # TestNG XML suite files for grouping and running tests
├── Jenkinsfile            # Jenkins pipeline configuration
└── pom.xml                # Maven configuration and dependencies
```

## 🛠️ Prerequisites

Before you begin, ensure you have the following installed on your machine:

1.  **Java Development Kit (JDK):** Version 17 (as specified in `pom.xml`).
2.  **Maven:** Version 3.8+ for dependency management and test execution.
3.  **Git:** For version control.
4.  **Web Browsers:** The browsers you intend to test on (Chrome, Firefox, Edge, etc.).

## 🚀 Installation & Setup

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/SaifAlAbabseh/selenium-web-automation-framework.git
    cd All-Chat-UI-Test-Automation
    ```

2.  **Environment Variables:**
    *   Create a `.env` file in the root directory (or `/var/Env/.env` for Jenkins) if your tests require specific environment configurations (e.g., credentials, base URLs). 
    *   *Note: A template `.env` might be required based on your `utils` implementation.*

3.  **Install Dependencies:**
    Run the following command to download all necessary Maven dependencies:
    ```bash
    mvn clean install -DskipTests
    ```

## 🏃‍♂️ Running the Tests

You can run the tests locally using Maven. The framework supports passing dynamic properties to control the execution environment.

### Basic Run

To run the tests using the default settings and a specific TestNG suite file:

```bash
mvn clean install -DsuiteXmlFile=suites/all_chat_project/MainTestSuite.xml
```

### Passing Parameters

You can customize the test execution by passing system properties. Here are the common properties based on the framework setup:

*   `-Dbrowser`: Specify the browser to run tests on (e.g., `chrome`, `firefox`, `edge`).
*   `-DheadlessMode`: Set to `true` to run tests in the background without a UI, or `false` to see the browser.
*   `-DmobileMode`: Set to `true` to simulate a mobile viewport (e.g., 500x900).

**Examples:**

1.  **Run in Headless Mode on Chrome:**
    ```bash
    mvn clean install -DsuiteXmlFile=suites/MainTestSuite.xml -Dbrowser=chrome -DheadlessMode=true
    ```

2.  **Run in Mobile View (Not Headless):**
    ```bash
    mvn clean install -DsuiteXmlFile=suites/MainTestSuite.xml -Dbrowser=chrome -DheadlessMode=false -DmobileMode=true
    ```

## 📊 Viewing Reports

After the test execution finishes, Cucumber generates reports in the `target` directory:

*   **HTML Report:** Open `target/cucumber-report.html` in your web browser for a detailed, interactive view of the test results.
*   **JSON Report:** `target/cucumber-report.json` is generated for integration with CI/CD tools or custom reporting dashboards.
*   **Screenshots:** If any tests fail, screenshots are typically saved in `src/main/screenshots/`.

## 🔄 CI/CD (Jenkins)

The project includes a `Jenkinsfile` configured with stages for:
1.  Cleaning the workspace and checking out the code.
2.  Setting environment variables from a `.env` file.
3.  Generating a unique virtual display for UI tests (Xvfb) when not in headless mode.
4.  Running tests with video recording (ffmpeg).
5.  Publishing artifacts (reports, screenshots, videos).
6.  Sending Slack notifications with test summaries and video uploads to a designated channel.
