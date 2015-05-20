package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;

public class FavoritesScreen extends HikeLibrary{
	
	public FavoritesScreen() {
		waitForFavoritePageToLoad();
	}

	protected By favoritesHeader = MobileBy.name("Favorites");
	protected By backButton = MobileBy.name("All Conversations");
	protected By searchOrEnterNumber = MobileBy.IosUIAutomation(".searchBars()[0]");
	
	protected By favoritesTab = MobileBy.name("FAVORITES");
	protected By peopleOnHikeTab = MobileBy.name("PEOPLE ON HIKE");
	protected By smsContactsTab = MobileBy.name("SMS CONTACTS");
	
	protected By favoriteContactsCount = MobileBy.IosUIAutomation(".tableViews()[0].groups()[0].staticTexts()[1]");
	protected By peopleOnHikeContactsCount = MobileBy.IosUIAutomation(".tableViews()[0].groups()[2].staticTexts()[1]");
	
	protected By addFavoritesBy = MobileBy.name("Add favorites");
	protected String addFavoritesText = "Add favorites"; 
	
	protected String allContactsPrefix = ".tableViews()[0].cells()";
	protected String firstContact = ".tableViews()[0].cells()[0]";
	protected String contactNameSuffix = ".staticTexts()[0]";
	protected String contactNumberSuffix = ".staticTexts()[1]";
	
	protected By firstUserAddFavButton = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].buttons()[0]");
	protected By removeButton = MobileBy.name("Remove");
	
	//getter methods
	
	public By getFavoritesHeader() {
		return favoritesHeader;
	}
	public By getBackButton() {
		return backButton;
	}
	public By getSearchOrEnterNumber() {
		return searchOrEnterNumber;
	}
	public By getFavoritesTab() {
		return favoritesTab;
	}
	public By getPeopleOnHikeTab() {
		return peopleOnHikeTab;
	}
	public By getSmsContactsTab() {
		return smsContactsTab;
	}
	public By getFavoriteContactsCount() {
		return favoriteContactsCount;
	}
	public By getPeopleOnHikeContactsCount() {
		return peopleOnHikeContactsCount;
	}
	public By getAddFavoritesBy() {
		return addFavoritesBy;
	}
	public String getAddFavoritesText() {
		return addFavoritesText;
	}
	public String getAllContactsPrefix() {
		return allContactsPrefix;
	}
	public String getFirstContact() {
		return firstContact;
	}
	public String getContactNameSuffix() {
		return contactNameSuffix;
	}
	public String getContactNumberSuffix() {
		return contactNumberSuffix;
	}
	
	public By getRemoveButton() {
		return removeButton;
	}

	//methods
	
	public void addUserAsFavorite() {
		clickOnElement(firstUserAddFavButton);
	}
	
	public void removeUserFromFavorites() {
		
		try {
			WebElement favUserWebElement = driver.findElement(MobileBy.IosUIAutomation(firstContact));
			MobileElement favoriteUser = (MobileElement) favUserWebElement;
			int y = favUserWebElement.getLocation().getY();
			//int x = driver.manage().window().getSize().width;
			//int leftX = favoriteUser.getLocation().getX();
			//int leftY = favUserWebElement.getLocation().getY();
			new TouchAction(driver).press(favUserWebElement).moveTo(0, y).perform();
			favoriteUser.swipe(SwipeElementDirection.RIGHT, 1000);
			clickOnElement(removeButton);
		} catch (Exception e) {
			Reporter.log("Unable to remove from favorites by automation.");
		}
	}
	
	private void waitForFavoritePageToLoad() {
		boolean pageLoaded = false;
		int counter = 0; 
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(getFavoritesHeader());
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}
	
}
