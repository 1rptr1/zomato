package Appium2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static Appium2.UI_Automator2_Options.stopEmulator;

public class appium2 {
    static AppiumDriverLocalService service;
    static AndroidDriver driver;
    static String port="4723";
    static void setInstance(){
        String userHome = System.getProperty("user.home");
        String nodeJsMainPath =userHome + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
        String nodeExe ="C:\\Program Files\\nodejs\\node.exe";
        String log = "target\\log.txt";


        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withAppiumJS(new File(nodeJsMainPath))
                .usingDriverExecutable(new File(nodeExe))
                .usingPort(Integer.parseInt(port))
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withLogFile(new File(log))
                .withIPAddress("127.0.0.1");

        service = AppiumDriverLocalService.buildService(builder);
    }
    static AppiumDriverLocalService getInstance()
    {
        if(service==null)
            setInstance();

        return service;
    }
public static void startAppiumServer()
{
    AppiumServerManager.startAppiumServer(port);
    getInstance().start();
    System.out.println("*****************Starting Appium Server*****************");
    System.out.println("URL : " + service.getUrl());
    System.out.println("Is Server running " + service.isRunning());
}

public static void stopAppiumServer()
{
    if(service!=null)
        getInstance().stop();
    AppiumServerManager.stopAppiumServer();
    System.out.println("*****************Appium Server Stopped*****************");
}

    public static void main(String[] args) throws MalformedURLException {
        appium2.startAppiumServer();
        UI_Automator2_Options options = new UI_Automator2_Options();
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"),options.getCalculatorAPKOptions());
        driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_sub")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_pct")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_div")).click();
        stopEmulator();
        appium2.stopAppiumServer();
    }


}
