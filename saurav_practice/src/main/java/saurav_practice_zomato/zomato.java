package saurav_practice_zomato;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import saurav_practice_base.SetUp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.html5.Location;
import org.testng.annotations.*;

public class zomato {

	SetUp android_Setup;
	AppiumDriverLocalService service;
	static AppiumDriver<MobileElement> driver;

	public zomato() throws IOException {

		try {
			android_Setup = new SetUp();
			android_Setup.adb_executor_emulator("Pixel_XL_API_30");
			android_Setup.capabilities.setCapability("app", "D:\\appium_Automation\\zomato.apk");
			service = AppiumDriverLocalService.buildService(android_Setup.builder);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@BeforeClass
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setUp() throws MalformedURLException, InterruptedException {
		service.stop();
		Thread.sleep(50);
		service.start();
		URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver(remoteUrl, android_Setup.capabilities);
	}

	@Test
	public void sampleTest() throws InterruptedException {

		try {
			Thread.sleep(10000);
			//SetUp.explicit_Wait_elementnotfound(driver, zomato_elements.gps_permission_allow_this_time); 
			
			//driver.findElement(zomato_elements.gps_permission_allow_this_time).click();;

			driver.setLocation(new Location(19.290778, 84.787631, 40));
			driver.findElement(zomato_elements.mobile_number).click();
			System.out.println(((HasOnScreenKeyboard) driver).isKeyboardShown());
			driver.findElement(zomato_elements.mobile_number).sendKeys("7381670585");
			SetUp.explicit_Wait_visibility(driver, zomato_elements.skip);
			driver.findElement(zomato_elements.skip).click();
			

			SetUp.explicit_Wait_visibility(driver, zomato_elements.title);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		android_Setup.adb_kill_app("com.application.zomato");
		driver.quit();
		android_Setup.adb_kill_Emulator();
	}

}
