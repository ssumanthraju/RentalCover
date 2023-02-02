package com.qa.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.Base;

public class HomePage {
	Base base;
	
	public HomePage() {
		base = new Base();
		PageFactory.initElements(base.driver,this);
	}
	
	@FindBy(xpath = "(//input[@placeholder='Select or type a country'])[1]")  WebElement countryFld;
	@FindBy(xpath = "(//button[contains(text(),'ACCEPT')])[1]") WebElement acceptCookies;
	@FindBy(xpath = "//ul[@id='ui-id-1']/li/div") List<WebElement> CountryList_RentingVehicle;
	@FindBy(xpath = "//ul[@id='ui-id-5']/li") List<WebElement> CountryList_CountryOfResidence;
	@FindBy(xpath = "//div[@class='ui-menu-item-wrapper'][1]") WebElement CountrySelect;
	@FindBy(xpath = "//strong[contains(text(),'change')]//ancestor::div[contains(text(),'I live in:')]") WebElement Change_CountryOfResidence;
	@FindBy(xpath = "//strong[contains(text(),'change')]//ancestor::div[contains(text(),'renting')]") WebElement Change_RentingVehicle;
	@FindBy(xpath = "(//input[@placeholder='Type a country'])[1]") WebElement inputCountryOfResidence;
	@FindBy(xpath = "//input[@id='QuoteForm_FromDate-datepicker']") WebElement FromDate;
	@FindBy(xpath = "//input[@id='QuoteForm_ToDate-datepicker']") WebElement ToDate;
	
	@FindBy(xpath = "(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]/div[1]") WebElement datePickerGroupFirst;
	@FindBy(xpath = "(//div[@class='ui-datepicker-group ui-datepicker-group-last'])[1]/div[1]") WebElement datePickerGroupLast;
	@FindBy(xpath = "//div[@class='ui-datepicker-group ui-datepicker-group-first']/table[@class='ui-datepicker-calendar']//td[@data-handler='selectDay']") List<WebElement> datePickerFirst_Days;
	@FindBy(xpath = "//div[@class='ui-datepicker-group ui-datepicker-group-last']/table[@class='ui-datepicker-calendar']//td[@data-handler='selectDay']") List<WebElement> datePickerLast_Days;
	@FindBy(xpath = "//a[@data-handler='next']") WebElement nextMonth;
	@FindBy(xpath = "//div[@class='ui-datepicker-group ui-datepicker-group-first']//span[@class='ui-datepicker-month']") WebElement calendarDispFirstMonth;
	@FindBy(xpath = "//div[@class='ui-datepicker-group ui-datepicker-group-first']//span[@class='ui-datepicker-year']") WebElement calendarDispFirstYear;
	@FindBy(id = "QuoteForm_VehicleType") WebElement selectRentingVehicle;
	@FindBy(xpath = "//span[@class='btn-text' and contains(text(),'Instant')]//ancestor::button[@type='submit']") WebElement submitInstantQuote;
	
	public HomePage	selectRentingCountry(String Country) throws InterruptedException {
		base.SendKeys(countryFld,"Select or type a country",Country);
		Iterator<WebElement> itr = CountryList_RentingVehicle.iterator();
		//Iterate dynamic drop down elements and Click based on input
		while(itr.hasNext()) {
			JavascriptExecutor j = (JavascriptExecutor)base.GetDriver();
			j.executeScript("arguments[0].click();", itr.next());
			break;
		}		
		return this;		
	}
	
	public HomePage	selectCountryOfResidence(String Country) throws InterruptedException {
		base.SendKeys(inputCountryOfResidence,"Country of Residence", Country);
		Iterator<WebElement> itr = CountryList_CountryOfResidence.iterator();
		//Iterate dynamic drop down elements and Click based on input
		while(itr.hasNext()) {
			JavascriptExecutor j = (JavascriptExecutor)base.GetDriver();
			j.executeScript("arguments[0].click();", itr.next());
			break;
		}		
		return this;		
	}
		
	public HomePage clickCountry() {
		base.ClickElement(countryFld,"Select or type Country");
		return this;
	}
	
	public HomePage clickFromDate(String inputDate) throws ParseException {
		base.ClickElement(FromDate,"From Date");
		selectDateFromCalendar(inputDate);
		return this;
	}
	
	public HomePage clickToDate(String toDate) throws ParseException {
		base.ClickElement(ToDate, "To Date");
		selectDateFromCalendar(toDate);
		return this;
	}
	
	public void ClickDay(List<WebElement> weList,String Day) {
		for(WebElement we:weList) {
			if(we.getText().trim().equalsIgnoreCase(Day)) {
				we.click();
				break;
			}
		}
	}
	
	public HomePage selectDateFromCalendar(String inputDate) throws ParseException {
		Date d1 = new SimpleDateFormat("dd-MMM-yyyy",Locale.US).parse(inputDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		String inputMonth = cal.getDisplayName(cal.MONTH, cal.LONG, Locale.getDefault());
		String inputYear = Integer.toString(cal.get(cal.YEAR));
		String Day =  Integer.toString(cal.get(cal.DAY_OF_MONTH));	
		
		//compare current date with input date and get months difference
		DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH);
		LocalDate inputDateLocal = LocalDate.parse(inputDate, df);
		//LocalDate currentDate = LocalDate.now();
		
		String CalDispFirstMonth = calendarDispFirstMonth.getText();
		String CalDispFirstYear = calendarDispFirstYear.getText();
		
		SimpleDateFormat originalInputFormat = new SimpleDateFormat("MMMM yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		String calFirstDate = targetFormat.format(originalInputFormat.parse(CalDispFirstMonth+" "+CalDispFirstYear)).replace(".","");
		LocalDate calFirstDateLocal = LocalDate.parse(calFirstDate,df);
		
		//System.out.println(df.format(calFirstDateLocal));
		//System.out.println(df.format(inputDateLocal));
		
		int DiffMonths = (int)ChronoUnit.MONTHS.between(calFirstDateLocal.withDayOfMonth(1),inputDateLocal.withDayOfMonth(1));
		
		String inputMonth_Year = inputMonth +" "+ inputYear;
		if(DiffMonths==0) { //From date is in current Month
			if(datePickerGroupFirst.getText().contains(inputMonth_Year))
				ClickDay(datePickerFirst_Days, Day);
		}else if(DiffMonths==1) { //From date is in next Month
			if(datePickerGroupLast.getText().contains(inputMonth_Year))		
				ClickDay(datePickerLast_Days, Day);
		}else if(DiffMonths>1) { //Move to next months until desired input month is found
			for(int monthIterate=2;monthIterate<=DiffMonths;monthIterate++) {
				base.ClickElement(nextMonth,"Next Month");				
			}
			if(datePickerGroupLast.getText().contains(inputMonth_Year))		
				ClickDay(datePickerLast_Days, Day);
		}
		return this;
	}
	
	public HomePage AcceptCookies() {
		base.ClickElement(acceptCookies,"Accept Cookies");
		return this;
	}

	public HomePage click_Change_CountryofResidence() 
	{
		base.ClickElement(Change_CountryOfResidence,"Change-Country Of Residence");
		return this;
	}
	public HomePage click_Change_RentingVehicle() throws InterruptedException 
	{
		Thread.sleep(500);
		base.ClickElement(Change_RentingVehicle,"Change-RentVehicle");
		return this;
	}
	public HomePage select_RentingVehicle(String value)
	{
		base.SelectElement(selectRentingVehicle, value);
		return this;
	}
	public PolicyInformationPage GetInstantQuote() {
		base.ClickElement(submitInstantQuote,"Submit Quote");
		return new PolicyInformationPage();
	}
	
	public HomePage Enter_CountryOfResidence(String CountryOfResidence) {
		base.SendKeys(inputCountryOfResidence,"Country of Residence", CountryOfResidence);
		//base.ClickElement(inputCountryOfResidence);		
		//inputCountryOfResidence.sendKeys("United States");
		return this;		
	}
}
