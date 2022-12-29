package com.qa.tests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger.Level;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.seleniumhq.jetty9.server.Server;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import com.qa.utils.commonFunctions;

import io.appium.java_client.screenrecording.CanRecordScreen;
import io.netty.handler.codec.base64.Base64;
public class ProductTests {
	BaseTest base;	
	LoginPage loginPage;
	ProductsPage productsPage;
	ProductDetailsPage productDetailsPage;
	MenuPage menuPage;
	SettingsPage settings;
	InputStream io;
	JSONObject jsonObj;
	
	@Parameters({"platformName","deviceName","UDID","unlockType","unlockKey"})
	@BeforeClass
	  public void beforeClass(String platformName,String deviceName,String UDID,String unlockType,String unlockKey) throws JSONException, IOException
	  {
		  try {
			base = new BaseTest();	
			base.InitialiseDriver(platformName, deviceName, UDID, unlockType, unlockKey);
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
	@Test(priority=1)
	public void login() throws Exception{
		//productsPage = loginPage.Login(jsonObj.getJSONObject("validcredentials").getString("username"),jsonObj.getJSONObject("validcredentials").getString("password"));
		
		ProductsPage productsPage = new ProductsPage();
	}
	
	@Test(priority=2)
	public void validateProductOnProductsPage() throws Exception {			
			commonFunctions.OpenDeepLinkURI("swaglabs://swag-overview/0,1", "com.swaglabsmobileapp");
			//SoftAssert sa = new SoftAssert();
			 String SLBTitle =  productsPage.getSLBTitle();
			 String SLBPrice = productsPage.getSLBPrice();
			 Assert.assertEquals(SLBTitle,BaseTest.Hash_strings.get("products_SLB_Title"));
			 Assert.assertEquals(SLBPrice,BaseTest.Hash_strings.get("products_SLB_Price"));
			 //sa.assertAll();		
	}
	
	@Test(priority=3)
	public void verifyProductDetailsPage() throws Exception
	{		
		    commonFunctions.OpenDeepLinkURI("swaglabs://swag-overview/0,1", "com.swaglabsmobileapp");
			productDetailsPage = productsPage.clickSLBTitle();
			String actual_productDetailsTxt = productDetailsPage.GetslbTitle();
			String expected_productDetailsTxt = BaseTest.Hash_strings.get("products_SLB_Title");
			Assert.assertEquals(actual_productDetailsTxt, expected_productDetailsTxt);
			
			productDetailsPage.scrollToElement();			
			//Verify Price is displayed
			String actual_slbPrice = productDetailsPage.GetslbPrice();
			String expected_slbPrice = BaseTest.Hash_strings.get("products_SLB_Price");
			Assert.assertEquals(actual_productDetailsTxt, expected_productDetailsTxt);
			productsPage =  productDetailsPage.click_BckToProducts();	
	}
	
	@Test(priority=4)
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
		/*
		  //Record Screen
		  System.out.println("Before Method");
		  ((CanRecordScreen)base.GetDriver()).startRecordingScreen();
		*/ 
		  commonFunctions.log().info("\n ********Starting test:"+m.getName()+"***********\n");	  
	  }
	@AfterMethod
	public void RecordScreen(ITestResult result) throws IOException
	{/*
		System.out.println("After Method");
		String media =  ((CanRecordScreen)base.GetDriver()).stopRecordingScreen();
		Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		String dir = "Videos" + File.separator + params.get("platformName")+"_"
				+ params.get("deviceName")+"_"+params.get("UDID")+File.separator+commonFunctions.getDateTime()+File.separator+result.getTestClass().getRealClass().getSimpleName();
		File videoDir = new File(dir);
		if(!videoDir.exists()) videoDir.mkdirs(); //create directories if not exists
		FileOutputStream outputstream = new FileOutputStream(videoDir+File.separator+result.getName()+".mp4");
		outputstream.write(org.apache.commons.net.util.Base64.decodeBase64(media));
	*/
		 base.closeApp();
	}
	@AfterClass
	  public void quitDriver() {
		  base.closeApp();
		  base.quitDriver();
	  }
	@AfterSuite
	  public void afterSuite()
	  {
		  BaseTest.server.stop();
		  commonFunctions.log().info("Appium server stopped");
	  }
	  
	  @BeforeSuite
	  public void beforeSuite()
	  {
		  /*
		  System.setProperty("extent.reporter.html.start", "true");
	      System.setProperty("extent.reporter.html.config", "html-config.xml");
	      System.setProperty("extent.reporter.html.out", "test-output/ExtentHtml.html");
		  */
		  if(BaseTest.checkIfAppiumServiceIsRunning(4723))		  
			  commonFunctions.LogData("!!!!!!!Appium Server is already running!!!!!!!!!");		  
		  BaseTest.server = BaseTest.getAppiumService();
		  if(!(BaseTest.server.isRunning()))
		  {
			  BaseTest.server.start();
			  BaseTest.server.clearOutPutStreams();
			  commonFunctions.LogData("Appium server started...");
		  }
	  }
}
