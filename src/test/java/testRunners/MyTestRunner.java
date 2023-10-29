package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/AppFeatures"}, glue = {"stepDefinitions", "AppHooks"},
plugin = {"pretty",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class MyTestRunner extends AbstractTestNGCucumberTests {

}
