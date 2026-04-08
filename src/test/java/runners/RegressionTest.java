package runners;

import io.cucumber.testng.CucumberOptions;
import runners.base.BaseRunnerTest;

@CucumberOptions(
        tags = "@signup"
)
public class RegressionTest extends BaseRunnerTest {}
