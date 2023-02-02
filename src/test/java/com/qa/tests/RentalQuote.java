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
		  base.initialiseDriver();
		  base.loadStrings();
		  
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
  public void getInstantQuote() throws JSONException, InterruptedException, ParseException {
	  //homePg.EnterCountry(jsonObj.getJSONObject("InstantQuote").getString("country")); 
	  homePg.clickCountry(); //Click the country field
	  homePg.acceptCookies(); //Accept Cookies button on the pop up displayed
	  homePg.selectRentingCountry(jsonObj.getJSONObject("InstantQuote").getString("country"));
	  homePg.click_Change_CountryofResidence();
	  //Thread.sleep(2000);
	  //homePg.Enter_CountryOfResidence("United States");
	  homePg.selectCountryOfResidence(jsonObj.getJSONObject("InstantQuote").getString("country"));
	  homePg.clickFromDate(jsonObj.getJSONObject("InstantQuote").getString("fromDate"));
	  homePg.clickToDate(jsonObj.getJSONObject("InstantQuote").getString("toDate"));
	  homePg.clickChangeRentingVehicle();
	  homePg.select_RentingVehicle(jsonObj.getJSONObject("InstantQuote").getString("rentVehicle"));
	  policyInfoPg = homePg.getInstantQuote();
	  
	  String actualRentalCoverTxt = policyInfoPg.getRentalCoverText();	  
	  String expectedRentalCoverTxt = base.Hash_strings.get("validateRentalCoverageTxt");
	  Assert.assertTrue(actualRentalCoverTxt.contains(expectedRentalCoverTxt));
  }
  
  @AfterClass
  public void quitDriver() {
	  base.quitDriver();
  }
}
