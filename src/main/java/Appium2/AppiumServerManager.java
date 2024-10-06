package Appium2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppiumServerManager {

    private static Process appiumProcess;

    // Method to start Appium server
    public static void startAppiumServer(String port)  {
        String userHome = System.getProperty("user.home");
        String appiumPath = userHome + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

        // Construct command to run Appium server
        String command = "node " + appiumPath + " -p 4723";
        System.out.println(command);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        readProcessOutput(process);
    }

    static void startEmulator(String avdName) {
        try {
            String userHome = System.getProperty("user.home");
            String emulatorPath = userHome + "\\AppData\\Local\\Android\\Sdk\\emulator\\emulator.exe";

            // Start the emulator
            String command = emulatorPath + " -avd " + avdName;
            System.out.println(command);
            Process emulatorProcess = Runtime.getRuntime().exec(command);
            System.out.println("Starting emulator: " + avdName);

            // Wait a bit for the emulator to start (adjust this as necessary)
            Thread.sleep(5000); // Initial wait for the emulator process to start

            // Start the ADB server
            Runtime.getRuntime().exec("adb start-server");

            // Poll to check if the emulator is online (max wait 2 minutes)
            boolean emulatorReady = false;
            int waitTimeInSeconds = 200;  // max wait time
            int waited = 0;

            while (waited < waitTimeInSeconds) {
                readProcessOutput(emulatorProcess);
                Process adbProcess = Runtime.getRuntime().exec("adb devices");
                BufferedReader reader = new BufferedReader(new InputStreamReader(adbProcess.getInputStream()));
                String line;
                boolean deviceFound = false;

                // Read adb devices output and check for emulator status
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);  // Log adb output for debugging
                    if (line.contains("emulator") && line.contains("device")) {
                        deviceFound = true; // Emulator is online
                        emulatorReady = true;
                        break;
                    }
                }

                if (emulatorReady) {
                    System.out.println("Emulator is ready and online.");
                    break;
                } else {
                    System.out.println("Waiting for emulator to be ready...");
                    Thread.sleep(5000); // Wait 5 seconds before checking again
                    waited += 5;
                }
            }

            if (!emulatorReady) {
                System.out.println("Emulator did not start within the expected time.");
                throw new RuntimeException("Failed to start emulator: " + avdName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to stop Appium server
    public static void stopAppiumServer() {
        if (appiumProcess != null) {
            appiumProcess.destroy();
            System.out.println("Appium server stopped.");
        }
    }
    private static void readProcessOutput(Process process) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
