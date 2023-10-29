package com.pages;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.qa.util.Helper;

public class ChatPage {

	static Logger log = Logger.getLogger(ChatPage.class);

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	public static void config() {

		htmlReporter = new ExtentHtmlReporter("test-output/MyOwnReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	WebDriver ldriver;
	public Logger logger;

	public ChatPage(WebDriver rdriver) {

		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}


	@FindBy(xpath = "//textarea[@class='form-control']") 
	WebElement chatBox;

	@FindBy(xpath = "//button[@class='btn-start btn btn-alpha']") WebElement
	startLiveChatBtn;

	@FindBy(xpath = "//div[@class='message-box']/p/span[@class='sender']")
	List<WebElement> messageSender;

	@FindBy(xpath = "//textarea[@class='form-control input dialog-input']")
	WebElement chatBoxNew;

	@FindBy(xpath = "//button[@class='dialog-submit-btn btn btn-alpha']")
	WebElement sendBtn;

	@FindBy(xpath = "//button[@class='dialog-submit-btn btn btn-invert-beta']")
	WebElement endChatBtn;

	@FindBy(xpath = "//button[@class='btn btn-beta close-submit-btn-yes']")
	WebElement confirmYes;

	@FindBy(xpath="//span[@class='text'][text()='Hallo beim CCT-SUPPORT']")
	WebElement autoChatAgentMsg;

	@FindBy(xpath="//span[@class='error-code']") List<WebElement> errorCodes;


	public void startLiveChat(String chatText) throws InterruptedException {

		Thread.sleep(10000);
		Helper.enterText(chatBox, chatText);
		Helper.clickWebElement(startLiveChatBtn);
		Thread.sleep(15000);
		Helper.enterText(chatBoxNew, "This is the Test Chat, Thank You!");
		Helper.clickWebElement(sendBtn);
	}

	public void getChatHistory() {

		PropertyConfigurator.configure("log4j.properties");

		ExtentCucumberAdapter.addTestStepLog("<b><font color='red'>Chat Inititated</font></b>");

		List<WebElement> textMsg = ldriver.findElements(By.xpath("//span[@class='text']"));
		List<WebElement> msgSender = ldriver.findElements(By.xpath("//span[@class='sender']"));
		int listDifference = textMsg.size()-msgSender.size();
		for(int i=listDifference; i<textMsg.size()-listDifference;i++) {			

			ExtentCucumberAdapter.addTestStepLog("<b><font color='green'>>>>>>>>>>>>>>>>>>>>>>>></font></b>");

			String messageFrom = msgSender.get(i-listDifference).getAttribute("innerHTML");
			ExtentCucumberAdapter.addTestStepLog("Chat From: "+"<b><font color='blue'>"+messageFrom+"</font></b>");
			log.info("Chat From: "+messageFrom);

			String text = textMsg.get(i).getAttribute("innerHTML");
			ExtentCucumberAdapter.addTestStepLog("Chat Details: "+"<b>"+text+"</b>");
			log.info("Chat Details: "+text);			
		}		
	}

	public boolean verifyAgentOnline() {

		return Helper.waitForElementVisibility(ldriver, 30, autoChatAgentMsg);
	}

	public void continueChat(String textMsg) {
		Helper.enterText(chatBoxNew, textMsg);
		Helper.clickWebElement(sendBtn);

	}
	
public void continueChat(String textMsg1, String textMsg2, String textMsg3) throws InterruptedException {
		
		By agentTextMsg1 = By.xpath("//span[@class='text'][text()='Hallo wie geht es Ihnen']");
		By agentTextMsg2 = By.xpath("//span[@class='text'][text()='Wie kann ich Ihnen helfen']");
		By agentTextMsg3 = By.xpath("//span[@class='text'][text()='Es war schön, mit Ihnen zu sprechen, danke und einen schönen Tag']");
		Helper.enterText(chatBoxNew, textMsg1);
		Helper.clickWebElement(sendBtn);
		Thread.sleep(30000);
		Helper.enterText(chatBoxNew, textMsg2);
		Helper.clickWebElement(sendBtn);
		Helper.waitForElementVisibility(ldriver, 120, agentTextMsg2);
		Helper.enterText(chatBoxNew, textMsg3);
		Helper.clickWebElement(sendBtn);
		Helper.waitForElementVisibility(ldriver, 120, agentTextMsg3);
		Helper.enterText(chatBoxNew, "This is the Test Chat, Thank You!");
		Helper.clickWebElement(sendBtn);

	}

	public void endChat() throws InterruptedException {

		ExtentCucumberAdapter.addTestStepLog("<b><font color='green'>>>>>>>>>>>>>>>>>>>>>>>></font></b>");

		Thread.sleep(10000);
		Helper.clickWebElement(endChatBtn);
		Helper.clickWebElement(confirmYes);

		Assert.assertEquals(false, sendBtn.isEnabled(), "Verify that the Chat button is disabled after Chat is Ended");
		if(!sendBtn.isEnabled()) {
			ExtentCucumberAdapter.addTestStepLog("<b><font color='red'>Chat is Ended, Please initiate a new Chat</font></b>");
			log.info("Chat is Ended, Please initiate a new Chat");
		}

	}

	public void verifyErrorCodes() throws IOException, InterruptedException {
		Thread.sleep(10000);
		if(Helper.waitForElementsVisibility(ldriver, 30, errorCodes)) {
			for (WebElement errC : errorCodes) {
				Integer errorCode = Integer.parseInt(errC.getText());
				switch(errorCode) {		
				case 96:			
					ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(System.getProperty("user.dir")+"/reports/"+ExtentCucumberAdapter.getCurrentStep()+".png");
					ExtentCucumberAdapter.addTestStepLog("<b><font color='red'>ConfigException</font></b>");
					ldriver.quit();
					Assert.assertTrue(false, "Config Exception");			
					break;
				}
			}
		}
		else {
			ExtentCucumberAdapter.addTestStepLog("<b><font color='green'>System is Up and Running</font></b>");
		}
	}

}


