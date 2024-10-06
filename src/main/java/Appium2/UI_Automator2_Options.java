package Appium2;

import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static Appium2.AppiumServerManager.startEmulator;
import static Appium2.appium2.driver;

public class UI_Automator2_Options {
    String deviceName="Pixel_9_API_35";
    public UiAutomator2Options getCalculatorAPKOptions() {
        System.out.println("---Setting calculator options---");
       // adb_executor_emulator();
        //waitForEmulatorReady();
        // Get the root directory of the project
        startEmulator(deviceName);
        String projectDir = System.getProperty("user.dir");

        // Build the full path to the APK file
        String apkPath = projectDir + "\\APKs\\calculator.apk";

        // Check if the APK file exists before proceeding
        File file = new File(apkPath);
        if (!file.exists()) {
            throw new RuntimeException("APK file not found at: " + apkPath);
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android")
                .setPlatformVersion("15.0")
                .setAutomationName("uiautomator2")
                .setDeviceName("Pixel 9 API 35")
                .setAppActivity("com.android.calculator2.Calculator")
                .setAppPackage("com.google.android.calculator")
                .setApp(apkPath) // Set the correct APK path
                .setNoReset(false); // Install the app even if it's already present

        return options;
    }

    public void adb_executor_emulator() {
        String userHome = System.getProperty("user.home");

        // Define the path to the Android emulator directory dynamically
        String emulatorPath = userHome + "\\AppData\\Local\\Android\\Sdk\\emulator";

        // Full path to the emulator executable
        String emulatorExecutable = emulatorPath + "\\emulator.exe";

        // Build the command to run the emulator with the specified AVD
        ProcessBuilder processBuilder = new ProcessBuilder(emulatorExecutable, "-avd", deviceName);

        // Set the working directory to the emulator folder (optional, but ensures command context)
        processBuilder.directory(new File(emulatorPath));

        try {
            // Start the process
            Process process = processBuilder.start();

            // Optionally capture the output/error streams
            new Thread(() -> {
                try {
                    // Create a BufferedReader to read the output from the emulator
                    InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Wait for the process to exit (optional)
            int exitCode = process.waitFor();
            System.out.println("Emulator exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void waitForEmulatorReady() {
        while (waitForDeviceToBeReady()) {
            try {
                // Attempt to get the session status
                if (driver.getSessionId() != null) {
                    break; // Break loop when session is ready
                }
            } catch (Exception e) {
                System.out.println("Waiting for emulator to boot...");
                try {
                    Thread.sleep(5000); // Wait before retrying
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
        System.out.println("Emulator is ready!");
    }

    public Boolean waitForDeviceToBeReady() {
        boolean deviceReady = false;
        while (!deviceReady) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("adb", "devices");
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(deviceName) && line.contains("device")) {
                        deviceReady = true;
                        System.out.println("Device is ready.");
                        return true;
                    }
                }

                if (!deviceReady) {
                    System.out.println("Waiting for device to be ready...");
                    Thread.sleep(5000); // Wait before checking again
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static void stopEmulator()  {
        // Check if emulator is running
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean emulatorFound = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("emulator") && line.contains("device")) {
                    emulatorFound = true;
                    break;
                }
            }

            // Kill the emulator if it's found
            if (emulatorFound) {
                System.out.println("Stopping emulator...");
                Runtime.getRuntime().exec("adb -s emulator-5554 emu kill"); // emulator-5554 is the default name
                Thread.sleep(5000); // Wait for emulator to be killed
                System.out.println("Emulator stopped successfully.");
            } else {
                System.out.println("No emulator found running.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
