package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingsPage {
	BaseTest base;
	public SettingsPage()
	{
		base = new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(base.GetDriver()), this);
	}
	@AndroidFindBy(accessibility = "test-LOGOUT")WebElement Logout;
	
	public LoginPage pressLogOutBtn()
	{
		base.ClickElement(Logout);
		return new LoginPage();
	}

}
