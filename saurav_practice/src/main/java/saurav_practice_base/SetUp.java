package saurav_practice_base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SetUp {

	public DesiredCapabilities capabilities;
	public AppiumServiceBuilder builder;
	public SetUp()
	{
		builder = new AppiumServiceBuilder();

		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		builder.withArgument(GeneralServerFlag.RELAXED_SECURITY);

		capabilities = new DesiredCapabilities();

		capabilities.setCapability("avd","Pixel_XL_API_30"); 
		capabilities.setCapability("platformName","android");
		//capabilities.setCapability("automationName","espresso");
		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("fullReset",true);
		capabilities.setCapability("autoGrantPermissions", "true");

	}
	public void adb_executor_emulator(String devicename ) throws InterruptedException
	{
		ProcessBuilder pb_d = new ProcessBuilder("C:\\Android\\emulator\\emulator","-avd",devicename,"-no-snapshot");
		try {
			Process process = pb_d.start();
			process.waitFor(60, TimeUnit.SECONDS);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void adb_kill_Emulator() throws InterruptedException, IOException
	{
		String[] aCommand = new String[] { "C:\\Android\\platform-tools\\adb", "emu", "kill" };
		Process  process = new ProcessBuilder(aCommand).start();
		process.waitFor(1, TimeUnit.SECONDS);

	}
	
	public void adb_kill_app(String packagename) throws InterruptedException, IOException
	{
		String[] aCommand = new String[] { "C:\\Android\\platform-tools\\adb", "uninstall", "packagename" };
		Process  process = new ProcessBuilder(aCommand).start();
		process.waitFor(1, TimeUnit.SECONDS);

	}
	public static void swipe(WebDriver driver,int fromX,int fromY,int toX,int toY) {
		 
		 @SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction((PerformsTouchActions) driver);
		 action.press(PointOption.point(fromX,fromY))
		 .waitAction(new WaitOptions().withDuration(Duration.ofMillis(3000))) //you can change wait durations as per your requirement
		 .moveTo(PointOption.point(toX, toY))
		 .release()
		 .perform();
		 }
	
	
	public static void explicit_Wait_visibility(AppiumDriver<MobileElement> driver,By element)
	{
		WebDriverWait wait = new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	public static void explicit_Wait_elementnotfound(AppiumDriver<MobileElement> driver,By element)
	{
		WebDriverWait wait = new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public static void takeScreenshot(WebDriver driver, String screenShotName) {

	    File file  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    try {
	        FileUtils.copyFile(file, new File(screenShotName + ".jpg"));
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	public static void context(AppiumDriver<MobileElement> driver)
	{
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
		}
	}

}
