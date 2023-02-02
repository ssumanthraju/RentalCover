package com.qa.listeners;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

//import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Listeners;

import com.qa.*;
import com.qa.utils.*;


import io.netty.handler.codec.http.multipart.FileUpload;

public class TestNGListener implements ITestListener {

	public void onTestFailure(ITestResult result) 
	{		
		if(result.getThrowable()!=null) {
		  StringWriter sw = new StringWriter();
		  PrintWriter pw = new PrintWriter(sw);
		  result.getThrowable().printStackTrace(pw);
		  System.out.println(sw.toString());
		}
		
		TakesScreenshot scrShot =((TakesScreenshot)(WebDriver)Base.driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		
		HashMap<String, String> params =  (HashMap<String, String>) result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		String imagePath = "Screenshots" + File.separator + "RentalCover" +File.separator+commonFunctions.getDateTime()+File.separator+
				result.getTestClass().getRealClass().getSimpleName()+File.separator+result.getName()+".png";
		
		File file = ((TakesScreenshot) Base.driver).getScreenshotAs(OutputType.FILE);
		
		String completeImagePath = System.getProperty("user.dir")+File.separator+imagePath;
		
		try {
			org.apache.commons.io.FileUtils.copyFile(SrcFile, new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("This is the sample screen shot");
		Reporter.log("<a href='"+completeImagePath+"'><img src='"+completeImagePath+"' height='400' width='400'/></a>");		
		byte[] encoded = null;
	}
	
	
}
