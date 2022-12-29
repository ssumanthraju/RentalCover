package com.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	public static ExtentReports extent;
	public static ExtentSparkReporter reporter;
	static Map extentTestMap = new HashMap();
	
	public static ExtentReports getReporter()
	{
		if(extent == null)
		{
			extent = new ExtentReports(); //Create Extent reports object
			ExtentHtmlReporter reporter = new ExtentHtmlReporter("Extent_HTMLReport.html"); //Create Reporter object
			//Reporter config
			reporter.config().setDocumentTitle("Appium Framework");
			reporter.config().setReportName("SauceLabs");
			reporter.config().setTheme(Theme.DARK);
			extent.attachReporter(reporter); //Assign reporter to Extent reports object
		}		
		return extent; 	
	}
	
	public static ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int)(long)(Thread.currentThread().getId()));
	}
	
	public static ExtentTest startTest(String testName, String desc) {
		ExtentTest test = getReporter().createTest(testName,desc);
		extentTestMap.put((int)(long)(Thread.currentThread().getId()), test);
		return test;
		
	}
	
	
    
}
