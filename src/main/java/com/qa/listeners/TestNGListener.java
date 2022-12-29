package com.qa.listeners;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReport;
import com.qa.utils.commonFunctions;

import io.netty.handler.codec.http.multipart.FileUpload;

public class TestNGListener implements ITestListener {

	public void onTestStart(ITestResult result) {
		Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
		.assignCategory(params.get("platformName")+"_"+params.get("deviceName"))
		.assignAuthor("Sumanth");
	}
	
	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS,"Test Passed");
		ExtentReport.getReporter().flush();
	}
	
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
		ExtentReport.getReporter().flush();
	}
	
	public void onTestFailure(ITestResult result) 
	{		
		if(result.getThrowable()!=null) {
		  StringWriter sw = new StringWriter();
		  PrintWriter pw = new PrintWriter(sw);
		  result.getThrowable().printStackTrace(pw);
		  System.out.println(sw.toString());
		}
		
		TakesScreenshot scrShot =((TakesScreenshot)(WebDriver)BaseTest.driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		
		HashMap<String, String> params =  (HashMap<String, String>) result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		String imagePath = "Screenshots" + File.separator + params.get("platformName")+"_"+params.get("UDID")+
				"_"+params.get("deviceName")+File.separator+commonFunctions.getDateTime()+File.separator+
				result.getTestClass().getRealClass().getSimpleName()+File.separator+result.getName()+".png";
		
		//File file = ((WebDriver) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		
		String completeImagePath = System.getProperty("user.dir")+File.separator+imagePath;
		
		try {
			FileUtils.copyFile(SrcFile, new File(imagePath));
			Reporter.log("This is the sample screen shot");
			Reporter.log("<a href='"+completeImagePath+"'><img src='"+completeImagePath+"' height='400' width='400'/></a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		base2 = new BaseTest(); //initialise object
		//File file = base.GetDriver().getScreenshotAs(OutputType.FILE);
		File file = (((TakesScreenshot)base2.GetDriver())).getScreenshotAs(OutputType.FILE);
		System.out.println(file.getAbsolutePath());
		
		System.out.println("Screenshot file achieved.");
		try {
			FileUtils.copyFile(file, new File("C://SCREENSHOT.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//ExtentReport.getTest().log(Status.FAIL,"Test Failed");
		
		//convert srcFile into base64 byte array
		byte[] encoded = null;
		try {
			encoded = org.apache.commons.net.util.Base64.encodeBase64(FileUtils.readFileToByteArray(SrcFile));
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			ExtentReport.getTest().fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
			ExtentReport.getTest().fail(result.getThrowable().getMessage(), 
					MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded,StandardCharsets.US_ASCII)).build());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtentReport.getReporter().flush();
	}
	
	public void onFinish(ITestResult result)
	{
		ExtentReport.getReporter().flush();
	}

}
