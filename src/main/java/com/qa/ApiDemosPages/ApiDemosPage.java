package com.qa.ApiDemosPages;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseTest;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class ApiDemosPage {
	AndroidDriver driver;
	BaseTest base;
	public ApiDemosPage() {
		base = new BaseTest();
		this.driver=base.GetDriver();
		PageFactory.initElements(new AppiumFieldDecorator(base.GetDriver()), this);
	}
	
	@AndroidFindBy(accessibility = "Graphics") WebElement Graphics;
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"AlphaBitmap\"]") WebElement AlphaBitmap;
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"PathEffects\"]") WebElement PathEffects;
	
	public void clickGraphics() {
		base.ClickElement(Graphics);
	}
	
	public void PathEffectsScroll() {
		
		Dimension dim = driver.manage().window().getSize();
		int x = dim.getWidth()/2;
		int startY = (int)(dim.getHeight() * 0.8);
		int endY = (int)(dim.getHeight() * 0.2);
		TouchAction t = new TouchAction(driver);
	
		t.press(PointOption.point(x, startY))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
		.moveTo(PointOption.point(x, endY))
		.release()
		.perform();
		
		System.out.println(PathEffects.isDisplayed());
		
	}

}
