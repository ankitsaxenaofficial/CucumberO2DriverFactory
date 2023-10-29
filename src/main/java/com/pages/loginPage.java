package com.pages;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.util.*;

public class loginPage {
	
	
	WebDriver driver;
	
	@FindBy(xpath="(//button[text()='Login'])[2]")
	WebElement loginBtn;
	@FindBy(id="IDToken1")
	WebElement userName;
	@FindBy(id="login-username-submit-button")
	WebElement furtherBtn;
	@FindBy(id="login-password-submit-button")
	WebElement loginBtnAfterPwd;
	
	
	public loginPage(WebDriver driver) {
		
		this.driver =driver;
		PageFactory.initElements(driver, this);
		
		
	}

	public void Login(WebDriver driver) throws InterruptedException {
		
		this.driver = driver;
		Set<String> Child_id = driver.getWindowHandles(); 
		for(String win: Child_id) {
			
		driver.switchTo().window(win);
			Thread.sleep(10000);
		}
		
		Helper.clickWebElement(loginBtn);
		if(!driver.getCurrentUrl().contains("/auth/login?"))
			Helper.clickWebElement(loginBtn);
	}
	
	public void loginByRefNum(String uName, String pwd) {
		
		Helper.enterText(userName, uName);
		Helper.clickWebElement(furtherBtn);
		Helper.enterText(userName, pwd);
		Helper.clickWebElement(loginBtnAfterPwd);
	}
}
