package saurav_practice;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import saurav_practice_base.SetUp;
import saurav_practice_base.testUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.*;

public class calculator2 
{
	
	SetUp android_Setup;
	AppiumDriverLocalService service;
	static AppiumDriver driver;
	
	
	public calculator2() 
	{
		
		try 
		{
			android_Setup =new SetUp();
			android_Setup.adb_executor_emulator("Pixel_2_API_33");
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
			Thread.sleep(50);
			service.start();
			URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver(remoteUrl, android_Setup.capabilities);
	}
	
	@DataProvider
	public Iterator<Object[]> getTestdata() throws IOException 
	{
		testUtil testdat =new testUtil();
		return testdat.getTestDataFromText().iterator();
	}

	@Test(dataProvider="getTestdata")	
	public void sampleTest(String val1,String ope,String val2,String res) throws InterruptedException {
		//System.out.println(val1+" "+ope+" "+val2+" "+res);
		int data=0;
		//driver.findElementById("com.google.android.calculator:id/digit_"+val1).click();
		calculator_number_press(val1);
		data =Integer.valueOf(val1);
		if(ope.equalsIgnoreCase("+"))
			{driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
				data += Integer.valueOf(val2);
			}
		if(ope.equalsIgnoreCase("-"))
		{driver.findElement(By.id("com.google.android.calculator:id/op_sub")).click();
			data += Integer.valueOf(val2);
		}
		if(ope.equalsIgnoreCase("/"))
		{driver.findElement(By.id("com.google.android.calculator:id/op_div")).click();
			data += Integer.valueOf(val2);
		}
		if(ope.equalsIgnoreCase("%"))
		{driver.findElement(By.id("com.google.android.calculator:id/op_mod")).click();
			data += Integer.valueOf(val2);
		}
		calculator_number_press(val2);
		//driver.findElementById("com.google.android.calculator:id/clr").click();
		
		String expected = driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText();
		System.out.println("expected = "+expected);
		driver.findElement(By.id("com.google.android.calculator:id/clr")).click();
		
		Assert.assertEquals(Integer.valueOf(res),Integer.valueOf(expected));
	}
	
	public void calculator_number_press(String number)
	{
		for(int i=0;i<number.length();i++)
		{
			driver.findElement(By.id("com.google.android.calculator:id/digit_"+number.charAt(i))).click();
		}
	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		android_Setup.adb_kill_app("com.google.android.calculator");
		driver.quit();
		android_Setup.adb_kill_Emulator();
	}
	
}
