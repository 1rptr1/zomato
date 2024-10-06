Zomato Mobile Automation - Appium2
This repository contains the automation framework for testing the Zomato mobile application using Appium with Java and
TestNG. The primary goal is to automate UI tests for Android devices.

Project Structure
css
Copy code
zomato/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── Appium2/
│   │           ├── AppTest.java
│   │           ├── BaseTest.java
│   │           └── DriverManager.java
│   └── test/
│       └── java/
│           └── TestRunner.java
│
├── pom.xml
├── README.md
└── other project files...
Key Files
AppTest.java: Contains the test cases for verifying the functionality of the Zomato mobile app.
BaseTest.java: Sets up and tears down the test environment, including initializing Appium, setting desired capabilities,
and defining hooks for test execution.
DriverManager.java: Handles WebDriver instance management and ensures proper driver lifecycle control.
TestRunner.java: Runs the tests using TestNG and integrates the test execution flow.
Prerequisites
Java Development Kit (JDK) - Version 11 or higher.
Android Studio - For managing Android virtual devices (emulators).
Appium - Version 2.x.x or higher for mobile test automation.
Node.js - Required to run Appium Server.
Maven - For dependency management and building the project.
Environment Variables
Ensure the following environment variables are set:

ANDROID_HOME: Path to the Android SDK.
JAVA_HOME: Path to the JDK.
Setup Instructions
Clone the repository:

bash
Copy code
git clone https://github.com/1rptr1/zomato.git
cd zomato
Install dependencies: Make sure Maven is installed. Run the following command in the project root directory:

Copy code
mvn clean install
Start Appium Server: Open a terminal and start Appium using:

css
Copy code
appium -p 4723
Run the tests: You can run the test cases using TestNG with Maven:

bash
Copy code
mvn test
Emulator Setup
To execute tests on an Android emulator:

Set up an Android virtual device (AVD) in Android Studio (e.g., Pixel 9 API 35).
You can launch the emulator programmatically using the provided method in the code.
The tests will automatically execute once the emulator is fully booted.
Start Emulator Programmatically
You can start the emulator using the adb_executor_emulator method in the project:

java
Copy code
public void adb_executor_emulator(String deviceName) {
// Code to start the emulator using ProcessBuilder
}
Kill Emulator After Tests
To ensure that the emulator is terminated after the tests are done:

java
Copy code
public void killEmulator() {
// Code to terminate the emulator using adb commands
}
Appium Configuration
The UiAutomator2Options are set up in the BaseTest.java class to configure Appium for Android automation:

App Package: com.google.android.calculator
App Activity: com.android.calculator2.Calculator
Platform Name: Android
Device Name: Pixel 9 API 35
You can modify these configurations based on your testing requirements.

Key Features
Appium 2.x Integration: Uses the latest Appium version for mobile UI automation.
TestNG: Organizes test cases and provides robust test execution management.
Modular Framework: Cleanly separated test logic from driver management and Appium configuration.
Programmatic Device Management: Start, stop, and verify Android emulators programmatically.