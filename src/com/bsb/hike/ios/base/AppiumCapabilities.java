package com.bsb.hike.ios.base;

import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumCapabilities {
	public static IOSDriver driver;
	
	//TODO make appPath, deviceName, Version and udid generic.
	
	public static String appPath = "/Users/qa-lab/Documents/iosAutomation/IOSAutomation/Hike.ipa";
	public static String bundleId = "com.bsb.hike";
	public void setUp() throws Exception {
		boolean sessionCreated = false;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName","iPhone");
		capabilities.setCapability("platformName", "iOS" );
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, "7.1.2");
		capabilities.setCapability(CapabilityType.PLATFORM, "MAC");
		capabilities.setCapability("app", appPath);
		capabilities.setCapability("bundleId", bundleId);
		capabilities.setCapability("udid", "96819cc61af06b88f06eadb1d62944c473a54bb3");
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
