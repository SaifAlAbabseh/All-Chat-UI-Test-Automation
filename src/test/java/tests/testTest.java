package tests;

import org.testng.annotations.Test;
import utils.EnvConfig;

public class testTest {

    @Test(priority = 1, description = "Test the env varsssss")
    public void test() {
        System.out.println(EnvConfig.get("AC_USERNAME"));
    }
}
