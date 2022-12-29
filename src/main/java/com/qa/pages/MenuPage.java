package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MenuPage {
	BaseTest base;
	
	public MenuPage() {
		base = new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(base.GetDriver()), this);
	}
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView") WebElement settingsBtn;
	
	public SettingsPage pressSettingBtn()
	{
		base.ClickElement(settingsBtn);
		return new SettingsPage();
	}
	
	
		

}
