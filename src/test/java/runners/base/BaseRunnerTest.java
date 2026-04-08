package runners.base;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;

import java.io.File;

@CucumberOptions(
        features = "classpath:features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-report.json"}
)
public class BaseRunnerTest extends AbstractTestNGCucumberTests {

    @Parameters({"mobileMode", "browser", "headlessMode"})
    @BeforeTest
    public void setUpDriver(@Optional String mobileMode, @Optional String browser, @Optional String headlessMode) {
        DriverManager.setUp(mobileMode, browser, headlessMode);
    }

//    @AfterTest
//    public void handleCucumberReports() {
//        Object[] driverConfig = DriverManager.driverConfig.get();
//
//        boolean mobileMode = (Boolean)driverConfig[0];
//        String browserName = (String)driverConfig[1];
//        boolean headlessMode = (Boolean)driverConfig[2];
//
//        String fileName = String.format("cucumber-report-%s-%s-%s", mobileMode ? "Mobile View" : "Desktop View", browserName.toUpperCase(), headlessMode ? "Headless" : "Headed");
//
//        //Handle HTML report
//        File htmlReport = new File("target/cucumber-report.html");
//        File newHtmlReport = new File(String.format("target/%s.html", fileName));
//        if(htmlReport.exists()) {
//            htmlReport.renameTo(newHtmlReport);
//        }
//
//        //Handle JSON report
//        File jsonReport = new File("target/cucumber-report.json");
//        File newJsonReport = new File(String.format("target/%s.json", fileName));
//        if(jsonReport.exists()) {
//            jsonReport.renameTo(newJsonReport);
//        }
//    }
}
