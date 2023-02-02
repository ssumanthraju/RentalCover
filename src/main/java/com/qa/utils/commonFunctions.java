package com.qa.utils;

import java.io.File;
import java.lang.System.Logger.Level;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.xml.XMLConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class commonFunctions {
	private static final String filename = "src/test/resources/Strings/Strings.xml";
	public Hashtable<String,String> readXMLDOMParser() throws Exception {
		
		Hashtable<String, String> HashTbl_Strings = new Hashtable<String, String>();
		SAXBuilder sax = new SAXBuilder();
		// https://rules.sonarsource.com/java/RSPEC-2755
        // prevent xxe
        //sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        // XML is a local file
        Document doc = sax.build(new File(filename));
        Element rootNode = doc.getRootElement();
        
        List<Element> list = rootNode.getChildren();
        for (Element element : list) {
        	HashTbl_Strings.put(element.getAttributeValue("name"), element.getText());
		}
        return HashTbl_Strings;
	}
	
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}
	
	public static void logData(String message)
	{
		log().info(message);
	}
	
	public static void loglevelData(Level level,String message)
	{
		switch (level) {
		case INFO:
				log().info(message);	
			break;
		case DEBUG:
				log().debug(message);
				break;
		case ERROR:
				log().error(message);
				break;
		case WARNING:
				log().warn(message);
				break;
		default:
			break;
		}
	}
	
	public static Logger log() {
		return LogManager.getLogger((Thread.currentThread().getStackTrace()[2]).getClassName());
	}
	
}
