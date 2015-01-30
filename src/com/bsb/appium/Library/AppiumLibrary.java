package com.bsb.appium.Library;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.internal.TouchAction;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.common.Locators;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class AppiumLibrary extends AppiumCapabilities {

	public static boolean isElementPresent(By element) {
		if (driver.findElement(element).isDisplayed())
			return true;
		else
			return false;
	}

	// To click on any element
	public static void clickOnElement(By locator) {
		driver.findElement(locator).click();
	}

	public static void clickOnElement(String id) {
		driver.findElement(By.name(id)).click();
	}

	// To get the text for any element
	public static String getText(By locator) {
		System.out.println("gettexttttt......"+driver.findElement(locator).getAttribute("value"));
		return (driver.findElement(locator).getAttribute("value"));
	}

	// To check if particular element is enabled
	public static boolean isEnabled(By locator) {
		if (driver.findElement(locator).isEnabled())
			return true;
		return false;
	}

	// To enter some text in any element
	public static void enterText(By locator, String value) {
		driver.findElement(locator).sendKeys(value);

	}

	public static void doubleTapWithTwoFingers(final WebElement element) {
		
		((JavascriptExecutor) driver).executeScript("mobile: tap",
				new HashMap<String, Double>() {
					{
						put("tapCount", (double) 2);
						put("touchCount", (double) 2);
						put("duration", 0.5);
						put("x", (double) element.getLocation().getX());
						put("y", (double) element.getLocation().getY());
					}
				});
	}

}
