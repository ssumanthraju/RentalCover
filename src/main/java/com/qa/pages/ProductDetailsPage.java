package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class ProductDetailsPage {
	BaseTest base;
	AndroidDriver driver;
	public ProductDetailsPage()
	{
		base = new BaseTest();
		this.driver=base.GetDriver();
		PageFactory.initElements(new AppiumFieldDecorator(base.GetDriver()), this);
	}
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    WebElement slbTitle;
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-BACK TO PRODUCTS\"]/android.widget.TextView")
	WebElement BckToProducts;
	@AndroidFindBy(accessibility = "test-Price") WebElement slbPrice;
	
	
	public String GetslbTitle()
	{
		return base.GetAttribute(slbTitle, "text");
	}
	public String GetslbPrice()
	{
		return base.GetAttribute(slbPrice, "text");
	}
	public ProductsPage click_BckToProducts()
	{
		base.ClickElement(BckToProducts);
		return new ProductsPage();
	}
	
	public ProductDetailsPage scrollToElement()
	{
		//Scroll directly to the element of the page
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				         ".scrollIntoView(new UiSelector().description(\"test-Price\"))"
				));			
		//Scroll from Source Element to Target element of the page
		/*
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().description(\"test-Description\"))" +
				         ".scrollIntoView(new UiSelector().description(\"test-Price\"))"
				));
		*/
		return this;
	}
	
}
