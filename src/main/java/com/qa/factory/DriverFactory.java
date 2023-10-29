package com.qa.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
	
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver  = new ThreadLocal<WebDriver>();
	
	public WebDriver init_driver(String browser) {
		
		if(browser.equals("chrome")) {
			
			tlDriver.set(new ChromeDriver());
		}
		
		else {
			
			System.out.println("Please pass correct browser");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}
	
	public static synchronized WebDriver getDriver() {
		
		return tlDriver.get();
	}

}
