package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage{	
	BaseTest base;
	
	public LoginPage() {
		base=new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(BaseTest.driver), this);
	}
	
	@AndroidFindBy(accessibility = "test-Username") WebElement userNameFld;
	@AndroidFindBy(accessibility = "test-Password") WebElement passwordFld;
	@AndroidFindBy(accessibility = "test-LOGIN") WebElement login;
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private WebElement ErrorTxt;
	
	public ProductsPage Login(String username,String password)
	{
		enterUserName(username);
		enterPassword(password);
		return pressLoginBtn();
	}
	
	public LoginPage enterUserName(String userName) 
	{	
		base.SendKeys(userNameFld, userName);
		return this;
	}
	public LoginPage enterPassword(String password)
	{
		base.SendKeys(passwordFld, password);
		return this;
	}
	public ProductsPage pressLoginBtn()
	{
		base.ClickElement(login);
		return new ProductsPage();
	}
	public String getErrorText() 
	{
		return base.GetAttribute(ErrorTxt, "text");
	}
	
	
}