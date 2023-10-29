package stepDefinitions;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.pages.ChatPage;
import com.pages.homePage;
import com.pages.loginPage;
import com.qa.factory.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageSteps {
	
	private String title;
	WebDriver driver = DriverFactory.getDriver();
	private loginPage loginP = new loginPage(driver);
	private homePage homeP = new homePage(driver);
	private ChatPage chatP = new ChatPage(driver);
	
	//Scenario 1 - Chat Route
		@Given("User Launch Browser and opens URL {string}")
		public void user_launch_browser_and_opens_url(String url) throws InterruptedException {

			driver.get(url);		

			Thread.sleep(6000);

			try {
				JavascriptExecutor js = (JavascriptExecutor)driver;
				WebElement acceptCookie = (WebElement)js.executeScript("return document.querySelector(\"#usercentrics-root\").shadowRoot.querySelector(\"#uc-center-container > div > div.sc-iTVJFM.dIhOzs > div > div > div > button:nth-child(3)\")");

				js.executeScript("arguments[0].click();", acceptCookie);
			}catch (Exception e) {
				ExtentCucumberAdapter.addTestStepLog("Element not Visible. Continue...");
			}

		}


		@When("User clicks on link O2 Service live chat")
		public void user_clicks_on_link_o2_service_live_chat() {

			homeP.clickServiceChatLink();		
		}

		@Then("Update the Url {string}")
		public void update_the_url(String updatedUrl) {
			switchToLatestWindow();
			driver.get(updatedUrl);

		}
		@Then("click LoginButton")
		public void click_login_button() throws InterruptedException {

			switchToLatestWindow();
			loginP.Login(DriverFactory.getDriver());
			//base.captureScreenshot(null);
		}

		@When("User enters username as {string} and password as {string}")
		public void user_enters_username_as_and_password_as(String uName, String pwd) {

			loginP.loginByRefNum(uName, pwd);

		}

		@Then("Verify If System is Up")
		public void verify_if_system_is_up() throws IOException, InterruptedException {
			chatP.verifyErrorCodes();
		}

		@Then("start the chat {string}")
		public void start_the_chat(String chatMsg) throws InterruptedException {

			chatP.startLiveChat(chatMsg);
		}

		@Then("Verify if Agent is Online")
		public void verify_if_agent_is_online() {
			try {
				if(chatP.verifyAgentOnline()) {

					ExtentCucumberAdapter.addTestStepLog("<b><font color='green'>Agent Is Online</font></b>");
				}

			}catch(Exception e) {

				ExtentCucumberAdapter.addTestStepLog("<b><font color='red'>Chat is not routed to the agent. genesys Routing failed</font></b>");
				//driver.quit();
				Assert.assertTrue(false);
			}
		}

		/*
		 * @Then("Continue Chat") public void continue_chat() {
		 * chatP.continueChat("This is the Test Chat, Thank You!"); }
		 */
		
		@Then("Continue Chat {string} {string} {string}")
		public void continue_chat(String string, String string2, String string3) throws InterruptedException {
			
			chatP.continueChat(string, string2, string3);
		   
		}

		@Then("Get Chat History")
		public void get_chat_history() {

			chatP.getChatHistory();
		}

		@Then("End the Chat")
		public void end_the_chat() throws InterruptedException {

			chatP.endChat();
		}
		@Then("close the browser")
		public void close_the_browser() {

			//driver.quit();
			ExtentCucumberAdapter.addTestStepLog("Closing the browser");
		}

		public void switchToLatestWindow() {
			Set<String> Child_id = DriverFactory.getDriver().getWindowHandles(); 
			for(String win: Child_id) {

				DriverFactory.getDriver().switchTo().window(win);
			}
		}
}
