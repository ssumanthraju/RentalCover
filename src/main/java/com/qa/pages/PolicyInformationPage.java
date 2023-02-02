package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.Base;

public class PolicyInformationPage {
	Base base;
	public PolicyInformationPage() {
		base = new Base();
		PageFactory.initElements(base.driver,this);
	}
	
	@FindBy(xpath = "//dt[contains(text(),'You live in')]//following-sibling::dd[1]") 
	WebElement verify_YouLiveIn;
	
	@FindBy(xpath = "//dt[contains(text(),'Destination')]//following-sibling::dd[1]") 
	WebElement verify_Destination;
	
	@FindBy(xpath = "//dt[contains(text(),'Start date')]//following-sibling::dd[1]/span") 
	WebElement verify_RentalStartDate;
	
	@FindBy(xpath = "//dt[contains(text(),'End date')]//following-sibling::dd[1]/span") 
	WebElement verify_RentalEndDate;
	
	@FindBy(xpath = "(//*[contains(text(),'RentalCover.com coverage ')]//ancestor::div[1]//descendant::*[1])") 
	WebElement verify_RentalCoverageTxt;
	
	public String getRentalCoverText() {
		return base.getElementText(verify_RentalCoverageTxt);
	}
	

}
