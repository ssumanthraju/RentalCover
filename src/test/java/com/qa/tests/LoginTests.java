package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.commonFunctions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests {
	public AndroidDriver driver;
	LoginPage loginPage;
	ProductsPage productsPage;	
	MenuPage menuPage;
	SettingsPage settings;
	BaseTest base;
	JSONObject jsonObj;
	commonFunctions commFunc;

	InputStream io;
  @Test(priority = 1)
  public void invalidUserName() throws Exception {	  
		  loginPage.enterUserName(jsonObj.getJSONObject("invaliduser").getString("username"));
		  loginPage.enterPassword(jsonObj.getJSONObject("invaliduser").getString("password"));
		  loginPage.pressLoginBtn();
		  
	      //productsPage = loginPage.Login(jsonObj.getJSONObject("validcredentials").getString("username"),jsonObj.getJSONObject("validcredentials").getString("password"));				
		
	  
		  String actualErrorTxt = loginPage.getErrorText();
		  String expectedErrorTxt = BaseTest.Hash_strings.get("err_invalid_username_or_password");
		  System.out.println("Actual Error Messsage:"+actualErrorTxt);
		  
		  Assert.assertEquals(actualErrorTxt, expectedErrorTxt);
  }
  @Test(priority = 2)
  public void invalidPassword() throws Exception {
	  loginPage.enterUserName(jsonObj.getJSONObject("invalidpassword").getString("username"));
	  loginPage.enterPassword(jsonObj.getJSONObject("invalidpassword").getString("password"));
	  loginPage.pressLoginBtn();
	  
	  String actualErrorTxt = loginPage.getErrorText();
	  String expectedErrorTxt = BaseTest.Hash_strings.get("err_invalid_username_or_password");
	  System.out.println("Actual Error Messsage:"+actualErrorTxt);
	  
	  Assert.assertEquals(actualErrorTxt, expectedErrorTxt);
  }
  @Test(priority = 3)
  public void ValidCredentials() throws Exception {
	  loginPage.enterUserName(jsonObj.getJSONObject("validcredentials").getString("username"));
	  loginPage.enterPassword(jsonObj.getJSONObject("validcredentials").getString("password"));
	  loginPage.pressLoginBtn();
	  
	  //Verify products page is displayed
	  String actualProductsHdr = productsPage.getTitle();
	  String expectedProductsHdr = BaseTest.Hash_strings.get("products_title");
	  
	  Assert.assertEquals(actualProductsHdr, expectedProductsHdr);
  }
  @Test(priority = 4)
	public void logOut() throws Exception
	{
		settings = menuPage.pressSettingBtn();
		settings.pressLogOutBtn();	
	}
  @BeforeMethod
  public void beforeMethod(Method m) throws InterruptedException {
	  Thread.sleep(1000);
	  loginPage = new LoginPage();
	  productsPage = new ProductsPage();
	  menuPage = new MenuPage();
	  
	  System.out.println("\n ********Starting test:"+m.getName()+"***********\n");	  
  }

  @AfterMethod
  public void afterMethod() {
  }
  
  @Parameters({"platformName","deviceName","UDID","unlockType","unlockKey"})
  @BeforeClass
  public void beforeClass(String platformName,String deviceName,String UDID,String unlockType,String unlockKey) throws JSONException, IOException
  {
	  try {
		base = new BaseTest();
		base.InitialiseDriver(platformName, deviceName, UDID,unlockType,unlockKey);
		base.LoadStrings();
		base.launchApp();
		
		String dataFileName = "data/loginUsers.json";
		io = getClass().getClassLoader().getResourceAsStream(dataFileName);
		JSONTokener tokener = new JSONTokener(io);
		jsonObj = new JSONObject(tokener);
	  }catch(Exception e)
	  {
		  e.printStackTrace();
	  }finally {
		  if(io!=null) io.close();		
	}
  }

  @AfterClass
  public void quitDriver() {
	  base.closeApp();
	  base.quitDriver();
  }

}
