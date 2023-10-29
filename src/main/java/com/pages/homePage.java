package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.util.*;

public class homePage {
	
	WebDriver driver;
	
	public homePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	  @FindBy(xpath = "//a[@title='o2 Service Live-Chat']") WebElement
	  serviceChatLink;
	
	public void clickServiceChatLink() {
		//helper = new Helper();
		Helper.clickWebElement(serviceChatLink);
		
	}

}
