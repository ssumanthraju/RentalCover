package com.qa;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import com.qa.utils.commonFunctions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.*;

public class BaseTest {
	public static AndroidDriver driver;
	public static Properties props;
	public static Hashtable<String, String> Hash_strings = new Hashtable<String, String>();
    InputStream inputstream;
    commonFunctions commFunc;
    public static AppiumDriverLocalService server;
    //public static Logger log = LogManager.getLogger(BaseTest.class.getName()); 
    
    
    public void LoadStrings() throws Exception {
    	commFunc = new commonFunctions();
    	Hash_strings = commFunc.ReadXMLDOMParser();
    }
    
    //Implement Getters and Setters
    public void SetDriver(AndroidDriver driver)
    {
    	this.driver=driver;
    }
    public static AndroidDriver GetDriver() {
    	return driver;
    }
    
    public void InitialiseDriver(String platformName,String deviceName,String UDID,String unlockType,String unlockKey) {
	  try {
		  DesiredCapabilities caps;
		  props = new Properties();
		  String PropertyFileName = "config.properties";
		  inputstream = getClass().getClassLoader().getResourceAsStream(PropertyFileName);
		  props.load(inputstream);
		  /*
		  commonFunctions.log().info("This is INFO message");
		  commonFunctions.log().error("This is ERROR message");
		  commonFunctions.log().debug("This is DEBUG message");
		  commonFunctions.log().warn("This is WARN message");
		  */
		  caps = new DesiredCapabilities();
		  caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		  caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		  caps.setCapability(MobileCapabilityType.UDID, UDID);
		  
		  caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));		  
		  caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
		  caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
		  
		  caps.setCapability("unlockType", unlockType);
		  caps.setCapability("unlockKey", unlockKey);
		  URL appURL = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
		  File file = Paths.get(appURL.toURI()).toFile();	  
		  
		  //System.out.println(file.getAbsolutePath());
		  //caps.setCapability(MobileCapabilityType.APP, file.getAbsolutePath());
		  //caps.setCapability(MobileCapabilityType.APP, "C:\\Users\\suman\\eclipse-workspace\\TDDFramework\\target\\test-classes\\app\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
		  
		  URL url = new URL(props.getProperty("appiumURL")); 
		  driver = new AndroidDriver(url,caps); 
		  commonFunctions.LogData("Created driver");
	  }catch(Exception e)
	  {
		 e.printStackTrace(); 
	  }
  }

public void WaitForElementToBeVisible(WebElement element)
  {
	  commonFunctions.LogData("waiting for element to be visible:"+ element);
	  //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	  wait.until(ExpectedConditions.visibilityOf(element));
  }
  
  public void ClickElement(WebElement element)
  {
	  WaitForElementToBeVisible(element);
	  commonFunctions.LogData(element.getText() + " clicked.");
	  element.click();
  }
  
  public void SendKeys(WebElement element, String text)
  {
	 WaitForElementToBeVisible(element);
	 element.sendKeys(text);
	 commonFunctions.LogData(element + " is keyed with text:"+text);
  }
  public String GetAttribute(WebElement element,String attribute)
  {
	  try {
		  WaitForElementToBeVisible(element);
		  return element.getAttribute(attribute);  
	  }catch(Exception e)
	  {
		  commonFunctions.LoglevelData(java.lang.System.Logger.Level.WARNING, "Unable to fetch attribute for element:"+element);
		  return "false";
	  }
	  
  }
  public void quitDriver() {
	  commonFunctions.LogData("Quitting session...");
	  driver.quit();
  }
  public void closeApp() {
	  commonFunctions.LogData("Closing application...");
	  driver.terminateApp(props.getProperty("androidAppPackage"));
  }
  public void launchApp()
  {
	  commonFunctions.LogData("Launching application:"+props.getProperty("androidAppPackage"));
	  driver.activateApp(props.getProperty("androidAppPackage"));
  }
  
  public static AppiumDriverLocalService getAppiumServiceDefault()
  {
	  return AppiumDriverLocalService.buildDefaultService();
  }
  
  public static AppiumDriverLocalService getAppiumService()
  {
	  return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
			  .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
			  .withAppiumJS(new File("C:\\Users\\suman\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
			  .usingPort(4723)
			  .withArgument(GeneralServerFlag.BASEPATH,"/wd/hub/")
			  .withArgument(GeneralServerFlag.SESSION_OVERRIDE));
  }
  
  public static boolean checkIfAppiumServiceIsRunning(int port)
  {
	  boolean isAppiumServiceRunning = false;
	  ServerSocket socket;
	  try {
		  socket = new ServerSocket(port);
		  socket.close();
	  }catch(IOException e) {
		  isAppiumServiceRunning=true;
	  }finally
	  {
		  socket=null;
	  }
	  return isAppiumServiceRunning;
  }

}
