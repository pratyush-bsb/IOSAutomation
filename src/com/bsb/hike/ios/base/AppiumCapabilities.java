package com.bsb.hike.ios.base;

import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumCapabilities {
	public static IOSDriver driver;
	
	//TODO make appPath, deviceName, Version and udid generic.
	
	public static String appPath = "/Users/kumarpratyush/Documents/workspace/IOSAutomation/Hike.app";
	public static String bundleId = "com.bsb.hike";
	public void setUp() throws Exception {
		boolean sessionCreated = false;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName","Pankaj");
		capabilities.setCapability("platformName", "iOS" );
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, "8.1.3");
		capabilities.setCapability(CapabilityType.PLATFORM, "MAC");
		capabilities.setCapability("app", appPath);
		capabilities.setCapability("bundleId", bundleId);
		capabilities.setCapability("udid", "63b94c683a786b34e090c1a72fe54b8cf82a34de");
		//capabilities.setCapability("showIOSLog", true);
		while(!sessionCreated) {
			try {
				driver = new IOSDriver (new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
				sessionCreated = true;
			} catch(SessionNotCreatedException e) {
				sessionCreated = false;
			}
		}
	}
}
