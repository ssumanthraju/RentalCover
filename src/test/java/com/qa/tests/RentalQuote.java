package com.qa.tests;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.*;
import com.qa.pages.HomePage;
import com.qa.pages.PolicyInformationPage;

public class RentalQuote {
	Base base;
	InputStream io;
	JSONObject jsonObj;
	HomePage homePg;
	PolicyInformationPage policyInfoPg;
  @BeforeClass
  public void beforeClass() {
	  try {
		  base = new Base();
		  base.InitialiseDriver();
		  base.LoadStrings();
		  
		  String input_InstantQuote = "data/InstantQuote.json";
		  io = getClass().getClassLoader().getResourceAsStream(input_InstantQuote);
		  JSONTokener tokener = new JSONTokener(io);
		  jsonObj = new JSONObject(tokener);
		  
	  }catch(Exception e) {
		  e.printStackTrace();
	  } 
  }
  
  @BeforeMethod
  public void beforeMethod(Method m) {
	  //Create Page Objects
	  homePg = new HomePage();
	  System.out.println("***Starting test:"+m.getName()+"****");
  }
  
  @Test(priority = 1)
  public void GetInstantQuote() throws JSONException, InterruptedException, ParseException {
	  //homePg.EnterCountry(jsonObj.getJSONObject("InstantQuote").getString("country")); 
	  homePg.clickCountry(); //Click the country field
	  homePg.AcceptCookies(); //Accept Cookies button on the pop up displayed
	  homePg.selectRentingCountry(jsonObj.getJSONObject("InstantQuote").getString("country"));
	  homePg.click_Change_CountryofResidence();
	  //Thread.sleep(2000);
	  //homePg.Enter_CountryOfResidence("United States");
	  homePg.selectCountryOfResidence(jsonObj.getJSONObject("InstantQuote").getString("country"));
	  homePg.clickFromDate(jsonObj.getJSONObject("InstantQuote").getString("fromDate"));
	  homePg.clickToDate(jsonObj.getJSONObject("InstantQuote").getString("toDate"));
	  homePg.click_Change_RentingVehicle();
	  homePg.select_RentingVehicle(jsonObj.getJSONObject("InstantQuote").getString("rentVehicle"));
	  policyInfoPg = homePg.GetInstantQuote();
  }
  @Test(priority=2)
  public void verifyRentalCoverInPolicyInformationPage() {
	  
	  String actualRentalCoverTxt = policyInfoPg.Get_RentalCoverText();	  
	  String expectedRentalCoverTxt = base.Hash_strings.get("validateRentalCoverageTxt");
	  if(actualRentalCoverTxt.contains(expectedRentalCoverTxt)) Assert.assertEquals(true, true);
	  else Assert.assertEquals(true, false);
  }
  
  @AfterClass
  public void quitDriver() {
	  base.quitDriver();
  }
}
