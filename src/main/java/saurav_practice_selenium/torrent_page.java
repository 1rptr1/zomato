package saurav_practice_selenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class torrent_page {
	WebDriver driver;
	public torrent_page() {
		System.setProperty("Webdriver.chrome.driver", "C:\\Users\\RptR\\Desktop\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("enable-automation");
		//options.addArguments("--headless"); 
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu"); 
		Map<String, Object> p = new HashMap<String, Object>();

		p.put("profile.managed_default_content_settings.images", 2);
		options.setExperimentalOption("prefs", p);
		driver= new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	@Test
	public void fetch() throws IOException 
	{
		driver.get("https://www.1377x.to/search/Kung.Fu.Panda.%282008%29.720p.BluRay/1/");

		File file = new File("C:\\Users\\RptR\\Desktop\\top.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		//List<String> notfound = new ArrayList<String>();
		while ((st = br.readLine()) != null)
		{ 	
			if (st.length()>0) {
				System.out.println(st.trim().replace(" ", ".")+".720p.BluRay");
				driver.findElement(By.xpath("//input[@id='autocomplete']")).click();
				driver.findElement(By.xpath("//input[@id='autocomplete']")).clear();
				driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys(st.trim().replace(" ", " ")+" 720p BluRay");
				driver.findElement(By.xpath("//button[@type='submit']")).click();
				List<WebElement> all_links=driver.findElements(By.xpath("//div[@class='table-list-wrap']/table/tbody/tr/td/a"));
				for(WebElement links:all_links)
				{	if(links.getAttribute("href").contains("torrent") && links.getAttribute("href").toLowerCase().contains("bluray"))
				{driver.get(links.getAttribute("href"));
				String data=driver.findElement(By.xpath("//a[contains(text(),'Magnet')]")).getAttribute("href");
				System.out.println(data);
				FileWriter  writer = new FileWriter ("C:\\Users\\RptR\\Desktop\\torrent_link.txt",true);
				writer.write("\n");
				writer.write(st.trim()+" : "+data);
				writer.write("\n");
				writer.close();
				break;
				}
				
				}
				
			}
		}
		
		

	}
	@AfterTest
	public void close()
	{
		driver.quit();
	}
	
	

}