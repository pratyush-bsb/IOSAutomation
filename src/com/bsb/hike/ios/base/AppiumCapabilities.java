package com.bsb.hike.ios.base;

import io.appium.java_client.ios.IOSDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumCapabilities {
	public static IOSDriver driver;

	//TODO make appPath, deviceName, Version and udid generic.

	public static String bundleId = "com.bsb.hike";

	public void setUp() throws Exception {
		boolean sessionCreated = false;
		String username = executeCommand("whoami");
		String appPathPrefix = "/Users/";
		String appPathSuffix = "/Documents/iosAutomation/IOSAutomation/";
		String appName = "Hike.ipa";
		String appPath = appPathPrefix + username.trim() + appPathSuffix + appName;
		String deviceID = executeCommand("/usr/local/bin/idevice_id -l");
		String deviceName = executeCommand("/usr/local/bin/idevicename -u " + deviceID);
		String deviceOSVersion = executeCommand("/usr/local/bin/ideviceinfo -u " + deviceID + " | grep ProductVersion");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("deviceName",deviceName.trim());
		capabilities.setCapability("platformName", "iOS" );
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, deviceOSVersion.trim());
		capabilities.setCapability(CapabilityType.PLATFORM, "MAC");
		capabilities.setCapability("app", appPath);
		capabilities.setCapability("bundleId", bundleId);
		capabilities.setCapability("udid", deviceID.trim());
		//capabilities.setCapability("showIOSLog", true);
		executeCommand("/usr/local/bin/ideviceinstaller -i "+appPath);
		while(!sessionCreated) {
			try {
				driver = new IOSDriver (new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
				sessionCreated = true;
			} catch(SessionNotCreatedException e) {
				sessionCreated = false;
			}
		}
		
	}

	public String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		String result = "";
		System.out.println(command);
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = "";			
			while ((line = input.readLine())!= null) {
				output.append(line + "\n");

			}
			result = output.toString();
		} catch (Exception e) {
			System.out.println("Unable to Execute Command "+ command);
			e.printStackTrace();
		}
		return result;
	}
}
