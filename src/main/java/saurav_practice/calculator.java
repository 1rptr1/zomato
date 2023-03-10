package saurav_practice;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import saurav_practice_base.SetUp;
import saurav_practice_base.testUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.*;

public class calculator 
{
	
	SetUp android_Setup;
	AppiumDriverLocalService service;
	static AppiumDriver<MobileElement> driver;
	
	
	public calculator() 
	{
		
		try 
		{
			android_Setup =new SetUp();
			android_Setup.adb_executor_emulator("Pixel_XL_API_33");
			android_Setup.capabilities.setCapability("appPackage", "com.google.android.calculator");
			android_Setup.capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
			android_Setup.capabilities.setCapability("app", "D:\\appium_Automation\\calculator.apk");
			service = AppiumDriverLocalService.buildService(android_Setup.builder);

		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
	}
	@BeforeClass
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setUp() throws MalformedURLException, InterruptedException 
	{
			service.stop();
			Thread.sleep(1000);
			service.start();
			Thread.sleep(1000);
			URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver(remoteUrl, android_Setup.capabilities);
			Thread.sleep(1000);
	}
	
	@DataProvider
	public Iterator<Object[]> getTestdata() throws IOException 
	{
		testUtil testdat =new testUtil();
		return testdat.getTestDataFromText().iterator();
	}

	@Test(dataProvider="getTestdata")	
	public void sampleTest(String data) throws InterruptedException {
		String expression[] = data.split(" ");
		for(int i=0;i<expression.length-2;i++)
		{
			try {
				Integer.valueOf(expression[i]);
				calculator_number_press(expression[i]);
			} catch (NumberFormatException e) {
				if(expression[i].equalsIgnoreCase("+"))
				{driver.findElementById("com.google.android.calculator:id/op_add").click();}
				if(expression[i].equalsIgnoreCase("-"))
				{driver.findElementById("com.google.android.calculator:id/op_sub").click();}
				if(expression[i].equalsIgnoreCase("%"))
				{driver.findElementById("com.google.android.calculator:id/op_pct").click();}
				if(expression[i].equalsIgnoreCase("/"))
				{driver.findElementById("com.google.android.calculator:id/op_div").click();}
				
			}
		}
			Double expected123 = Double.valueOf(driver.findElementById("com.google.android.calculator:id/result_preview").getText());
			Double val2 = Double.valueOf(expression[expression.length-1] );
			Assert.assertEquals(expected123,val2);
			driver.findElementById("com.google.android.calculator:id/clr").click();
			
	}
	
	public void calculator_number_press(String number)
	{
		for(int i=0;i<number.length();i++)
		{
			driver.findElementById("com.google.android.calculator:id/digit_"+number.charAt(i)).click();
		}
	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		android_Setup.adb_kill_app("com.google.android.calculator");
		driver.quit();
		android_Setup.adb_kill_Emulator();
	}
	
}
