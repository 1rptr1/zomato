package saurav_practice;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import saurav_practice_base.SetUp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.testng.annotations.*;

public class webvieweremulator2 
{
	
	SetUp android_Setup;
	AppiumDriverLocalService service;
	static AppiumDriver<MobileElement> driver;
	
	
	public webvieweremulator2() 
	{
		
		try 
		{
			android_Setup =new SetUp();
			android_Setup.adb_executor_emulator("Pixel_XL_API_30");
			android_Setup.capabilities.setCapability("chromedriverExecutable","D:\\appium_Automation\\chromedriver_83.exe");
			android_Setup.capabilities.setCapability("appPackage", "org.chromium.webview_shell");
			android_Setup.capabilities.setCapability("appActivity", "org.chromium.webview_shell.WebViewBrowserActivity");
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

	@Test
	public void sampleTest() throws InterruptedException {
		driver.get("https://www.google.com");
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context((String) contextNames.toArray()[1]);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
		WebElement hola =  driver.findElement(By.cssSelector(".gLFyf"));
		hola.sendKeys("shrek");
		hola.sendKeys(Keys.ENTER);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		SetUp.swipe(driver, 670, 1870, 670, 471);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(5000);
	
	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		
		driver.quit();
		android_Setup.adb_kill_Emulator();
	}
	
}
