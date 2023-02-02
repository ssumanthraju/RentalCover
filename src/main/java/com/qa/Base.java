package com.qa;

import org.testng.annotations.Test;
import com.qa.utils.TestUtils;
import com.qa.utils.commonFunctions;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Base {
	public static WebDriver driver;
	public static Properties props;
	public static Hashtable<String, String> Hash_strings = new Hashtable<String, String>();
    InputStream inputstream;
    commonFunctions commFunc;
    //public static Logger log = LogManager.getLogger(BaseTest.class.getName()); 
    
    
    public void LoadStrings() throws Exception {
    	commFunc = new commonFunctions();
    	Hash_strings = commFunc.ReadXMLDOMParser();
    }
    
    //Implement Getters and Setters
    public void SetDriver(RemoteWebDriver driver)
    {
    	this.driver=driver;
    }
    public static WebDriver GetDriver() {
    	return driver;
    }     
    
	public void InitialiseDriver() throws IOException, URISyntaxException {
    		DesiredCapabilities caps;
    		props = new Properties();
    		String PropertyFileName = "config.properties";
    		inputstream = getClass().getClassLoader().getResourceAsStream(PropertyFileName);
    		props.load(inputstream);
    		
    		
    		URL chromeDriverLocation = getClass().getClassLoader().getResource(props.getProperty("chromeDriverLocation"));
  		  	File file = Paths.get(chromeDriverLocation.toURI()).toFile();	  
  		  
  		  	System.out.println(file.getAbsolutePath());    		
    		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    		
    		ChromeOptions options = new ChromeOptions();
    		options.addArguments("--start-maximized");
    		options.setHeadless(false);
    		caps = new DesiredCapabilities();
    		caps.setCapability(ChromeOptions.CAPABILITY, options);
    		options.merge(caps);
    		
    		driver = new ChromeDriver(options);
    		driver.get(props.getProperty("URL"));
    } 

	public void WaitForElementToBeVisible(WebElement element)
	  {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		  wait.until(ExpectedConditions.visibilityOf(element));
	  }
  
	  public void ClickElement(WebElement element,String friendlyName)
	  {
		  WaitForElementToBeVisible(element);	  
		  element.click();
		  commonFunctions.LogData(friendlyName + " is clicked.");
	  }
	  
	  public void SelectElement(WebElement element,String value) {
		  WaitForElementToBeVisible(element);
		  Select selectElement = new Select(element);
		  selectElement.selectByVisibleText(value);
		  commonFunctions.LogData(element.getText() + " is selected with value:"+value);
	  }
  
	  public void SendKeys(WebElement element,String friendlyName, String text)
	  {
		 WaitForElementToBeVisible(element);
		 JavascriptExecutor j = (JavascriptExecutor)GetDriver();
		 j.executeScript("arguments[0].value='';", element);
		 element.sendKeys(text);
		 commonFunctions.LogData(friendlyName + " is keyed with text:"+text);
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
	  public String GetElementText(WebElement element)
	  {
		  try {
			  WaitForElementToBeVisible(element);
			  return element.getText();
		  }catch(Exception e)
		  {
			  commonFunctions.LoglevelData(java.lang.System.Logger.Level.WARNING, "Unable to fetch text for element:"+element);
			  return "false";
		  }
	  }  
  
	  public void quitDriver() {
		  commonFunctions.LogData("Quitting session...");
		  //driver.close();
		  driver.quit();
	  }  
}
