package com.bsb.hike.ios.base;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppiumCapabilities {
	public static WebDriver driver;
	    public void setUp() throws Exception {
		 DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName","Pankaj");
			capabilities.setCapability("platformName", "iOS" );
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		    capabilities.setCapability(CapabilityType.VERSION, "8.1.2");
		    capabilities.setCapability(CapabilityType.PLATFORM, "MAC");
		    capabilities.setCapability("app", "/Users/yashpreet/Downloads/Hikedevice.app");
		    capabilities.setCapability("bundleId", "com.bsb.hike");
		    capabilities.setCapability("udid", "63b94c683a786b34e090c1a72fe54b8cf82a34de");
		    capabilities.setCapability("showIOSLog", true);
		    driver = new RemoteWebDriver (new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	 }
}
