package com.bsb.appium.Library;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.base.AppiumCapabilities;

public class AppiumLibrary extends AppiumCapabilities {
	
	protected By pasteButton = MobileBy.name("Paste");
	
	public By getPasteButton() {
		return pasteButton;
	}

	public static boolean isElementPresent(By element) {
		try {
			driver.findElement(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isElementEnabled(By element) {
		boolean elementEnabled = false;
		try {
			elementEnabled = driver.findElement(element).isEnabled();
			
		} catch (Exception e) {
			elementEnabled = false;
		}
		return elementEnabled;
	}

	// To click on any element
	public static void clickOnElement(By locator) {
		try {
			driver.findElement(locator).click();
		} catch (Exception e) {
			Reporter.log("Unable to click on element : " + locator.toString());
			//e.printStackTrace();
		}
	}

	public static void clickOnElement(String id) {
		driver.findElement(By.name(id)).click();
	}

	// To get the text for any element
	public static String getTextByName(By locator) {
		String text = "";
		try {
			text = driver.findElement(locator).getAttribute("name");
			Reporter.log("Get text value for locator : " + locator.toString() + " Value : " + text);
		} catch (Exception e) {
			Reporter.log("Unable to get text for locator : " + locator);
		}
		
		return text;	
	}
	
	// To get the text for any element by it's value attribute
		public static String getTextByValue(By locator) {
			String text = "";
			try {
				text = driver.findElement(locator).getAttribute("value");
				Reporter.log("Get text value for locator : " + locator.toString() + " Value : " + text);
			} catch (Exception e) {
				Reporter.log("Unable to get text for locator : " + locator);
			}
			
			return text;	
		}

	// To check if particular element is enabled
	public static boolean isEnabled(By locator) {
		
		boolean isEnabled = false;
		try {
			if (driver.findElement(locator).isEnabled()) {
				isEnabled = true;
			}
		} catch (Exception e) {
			isEnabled = false;
		}
		return isEnabled;
	}

	// To enter some text in any element
	public static void enterText(By locator, String value) {
		try {
			driver.findElement(locator).sendKeys(value);
		} catch (Exception e) {
			Reporter.log("Unable to enter text : " + value + " in locator : " + locator.toString());
			e.printStackTrace();
		}

	}
	
	public static void enterTextWithClear(By locator, String value) {
		try {
			MobileElement element = (MobileElement) driver.findElement(locator);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			Reporter.log("Unable to enter text : " + value + " in locator : " + locator.toString());
			e.printStackTrace();
		}

	}

	public static void doubleTapWithTwoFingers(By by) {

		try {
			MobileElement hikeLogoElement = (MobileElement) driver.findElement(by);
			TouchAction action0 = new TouchAction(driver).tap(hikeLogoElement);
			TouchAction action1 = new TouchAction(driver).tap(hikeLogoElement);
			MultiTouchAction doubleTap = new MultiTouchAction(driver);
			doubleTap.add(action0).add(action1).perform();
			doubleTap.add(action0).add(action1).perform();
		} catch (Exception e) {
			Reporter.log("Unable to pick hike logo/double tap on hike logo");
			e.printStackTrace();
		}

	}
	
	//Method checks whether keyboard is visible or not
	public static boolean isKeyboardVisible() {
		
		boolean keyboardVisible = false;
		try {
			By deleteBy = MobileBy.name("Delete");
			driver.findElement(deleteBy);
			keyboardVisible = true;
		} catch (Exception e) {
			keyboardVisible = false;
		}
		
		return keyboardVisible;
	}
	
	public void toggleWifi() {
		NetworkConnectionSetting networkSettings = new NetworkConnectionSetting(true, true, true);
		networkSettings.setWifi(false);
	}
	
	public void performPartialDelete() {
		
		By deleteBy = MobileBy.name("Delete");
		clickOnElement(deleteBy);
	}
	
	//waits for 5 seconds (maximum) for keyboard to load
	public void waitForKeyboardToLoad() {
		
		By deleteBy = MobileBy.name("Delete");
		int counter = 0;
		while (counter < 5) {
			try {
				driver.findElement(deleteBy);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}
	
	//this does a long press on element found by the locator @param
	public void longPress(By locator) {
		
		try {
			WebElement element = driver.findElement(locator);
			new TouchAction(driver).longPress(element).perform();
		} catch(Exception e) {
			Reporter.log("Not able to find element");
		}
	}
	
	//this does a long press on the element passed as @param
	public void longPressByElement(WebElement element) {
		try {
			new TouchAction(driver).longPress(element).perform();
		} catch(Exception e) {
			Reporter.log("Unable to perform long press on pass element");
		}
	}
	
}
