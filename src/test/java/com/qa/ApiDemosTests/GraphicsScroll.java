package com.qa.ApiDemosTests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.ApiDemosPages.ApiDemosPage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;

public class GraphicsScroll {
	BaseTest base;
	InputStream io;
	JSONObject jsonObj;
	ApiDemosPage ApiDemos;
  @Test
  public void Graphics_Click() {
	  ApiDemos.clickGraphics();
	  ApiDemos.PathEffectsScroll();
  }
  
  @BeforeMethod
  public void BeforeMethod() {
	  ApiDemos = new ApiDemosPage();
  }
  
  
  @Parameters({"platformName","deviceName","UDID","unlockType","unlockKey"})
  @BeforeClass
  public void beforeClass(String platformName, String deviceName, String UDID, String unlockType, String unlockKey) throws Exception {
	  base = new BaseTest();
	  base.InitialiseDriver(platformName,deviceName,UDID,unlockType,unlockKey);
	  base.LoadStrings();
	  
	  String dataFileName = "data/loginUsers.json";
		io = getClass().getClassLoader().getResourceAsStream(dataFileName);
		JSONTokener tokener = new JSONTokener(io);
		jsonObj = new JSONObject(tokener);
  }

  @AfterClass
  public void afterClass() {
	  base.closeApp();
	  base.quitDriver();
  }

}
