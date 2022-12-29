package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductsPage  {
	BaseTest base;
	
	public ProductsPage()
	{
		base=new BaseTest();
		PageFactory.initElements(new AppiumFieldDecorator(base.GetDriver()), this);
	}
	@AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView") 
	WebElement productsHdr;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") WebElement slbTitle;
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]") WebElement slbPrice;
	
	public String getTitle() {
		return base.GetAttribute(productsHdr, "text");
	}
	
	public String getSLBTitle() {
		return base.GetAttribute(slbTitle, "text");
	}
	public String getSLBPrice() {
		return base.GetAttribute(slbPrice, "text");
	}
	public ProductDetailsPage clickSLBTitle() {
		base.ClickElement(slbTitle);
		return new ProductDetailsPage();
	}
}
