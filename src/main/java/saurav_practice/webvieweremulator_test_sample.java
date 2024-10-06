package saurav_practice;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

public class webvieweremulator_test_sample {

	static AppiumDriver driver;
	AppiumDriverLocalService service;
	@BeforeClass
	public void setUp() throws MalformedURLException, InterruptedException {


		ProcessBuilder pb_d = new ProcessBuilder("C:\\Android\\emulator\\emulator","-avd","Pixel_XL_API_33","-no-snapshot");
		try {
			System.out.println("Starting Device");
			Process process = pb_d.start();
			process.waitFor(60, TimeUnit.SECONDS);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		//builder.withCapabilities(capabilities);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("avd","Pixel_XL_API_33");
		//capabilities.setCapability("browserName", "MozillaFirefox"); 
		capabilities.setCapability("platformName","android");
		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("noReset",true);
		capabilities.setCapability("autoGrantPermissions", "true");
		
		//capabilities.setCapability("automationName ", "UiAutomator2");
		capabilities.setCapability("chromedriverExecutable","C:\\Users\\RptR\\Desktop\\chromedriver_83.exe");
		capabilities.setCapability("appPackage", "org.chromium.webview_shell");
		capabilities.setCapability("appActivity", "org.chromium.webview_shell.WebViewBrowserActivity");


		
		//Start the server with the builder
		 service = AppiumDriverLocalService.buildService(builder);
		 service.stop();
		 Thread.sleep(50);
		 service.start();





		URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

		driver = new AndroidDriver(remoteUrl, capabilities);

		
		//driver.quit();
	}

	@Test
	public void sampleTest() throws InterruptedException {
		driver.get("https://www.google.com");
	/*	Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context((String) contextNames.toArray()[1]);*/
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
		WebElement hola =  driver.findElement(By.cssSelector(".gLFyf"));
		hola.sendKeys("shrek");
		hola.sendKeys(Keys.ENTER);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		swipe(670, 1870, 670, 471);
		//driver.findElement(By.className("nav-input nav-progressive-attribute")).sendKeys("harma");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(5000);
	
	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		
		driver.quit();
		/*
		String[] aCommand = new String[] { "C:\\Android\\platform-tools\\adb", "uninstall", "org.mozilla.firefox" };
		 
		 Process process = new ProcessBuilder(aCommand).start();
		  process.waitFor(10, TimeUnit.SECONDS);
		 */
		String[] aCommand = new String[] { "C:\\Android\\platform-tools\\adb", "emu", "kill" };
		 
		 Process  process = new ProcessBuilder(aCommand).start();
		 
		  process.waitFor(1, TimeUnit.SECONDS);
		  service.stop();
	}
	public static void swipe(int fromX,int fromY,int toX,int toY) {
		 
		 /*TouchAction action = new TouchAction(driver);
		 action.press(PointOption.point(fromX,fromY))
		 .waitAction(new WaitOptions().withDuration(Duration.ofMillis(3000))) //you can change wait durations as per your requirement
		 .moveTo(PointOption.point(toX, toY))
		 .release()
		 .perform();
		 }*/
}}
