package com.qa.util;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class Helper {

	public static WebDriver driver;
	static WebElement element;
	public static void clickWebElement(WebElement ele) {

		ele.click();

	}

	public static void enterText(WebElement ele, String text) {

		ele.sendKeys(text);
	}


	public static void switchToLatestWindow() {
		
		Set<String> Child_id = driver.getWindowHandles(); 
		for(String win: Child_id) {
			driver.switchTo().window(win);
		}
	}

	public static boolean waitForElementVisibility(WebDriver ldriver, int timeOut, WebElement ele) {
		driver = ldriver;
		WebDriverWait wait  = new WebDriverWait(ldriver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOf(ele)).isDisplayed();

	}

	
	public static boolean waitForElementVisibility(WebDriver ldriver, int timeOut, By ele) {
		driver = ldriver;
		WebDriverWait wait  = new WebDriverWait(ldriver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOf(driver.findElement(ele))).isDisplayed();

	}
	public static boolean waitForElementsVisibility(WebDriver ldriver, int timeOut, List<WebElement> ele) {
		driver = ldriver;
		boolean flag=false;
		if (ele.size()>0) {
			
			for(WebElement e: ele) {
				String errCodeText = e.getText();
				if(errCodeText.trim().equals("")) {
					try {
					flag=false;
					}catch(Exception ex) {
						ExtentCucumberAdapter.addTestStepLog("Error Code Present");
					}
				}
				else {
					flag=true;
				}
			}
			
		}
		return flag;
		
	}
}

