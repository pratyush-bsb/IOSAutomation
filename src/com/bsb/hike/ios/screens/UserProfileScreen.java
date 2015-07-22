package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ProfileScreenInterface;

public class UserProfileScreen extends HikeLibrary implements ProfileScreenInterface {
	
	public UserProfileScreen(String userName) {
		this.userName = userName;
		waitForProfilePageToLoad();
	}
	

	//protected variables
	protected String userName;
	
	protected By profileScreenHeader = MobileBy.name("Profile");
	protected String profileScreenHeaderString = "Profile";
	protected By profilePic = MobileBy.name("avatarPlaceholderMedium0");
	
	protected By sendAMessage = MobileBy.IosUIAutomation(".tableViews()[0].groups()[0].buttons()[0]");
	protected By addAsFavorite = MobileBy.name("Add as a favorite");
	protected By addAsFavoriteStatus = MobileBy.IosUIAutomation(".tableViews()[0].groups()[0].staticTexts()[0]"); //get text by name, split on " ", second element is name
	
	private void waitForProfilePageToLoad() {
		
		int counter = 0;
		boolean pageLoaded = false;
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(userNameBy);
				pageLoaded = getTextByName(userNameBy).equalsIgnoreCase(this.userName);
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}
	
	//public getter methods
	
	public String getUserName() {
		return userName;
	}

	public By getInviteToHikeButton() {
		return inviteToHike;
	}

	public By getBackButtonProfileScreen() {
		return backButtonProfileScreen;
	}


	public By getProfileScreenHeader() {
		return profileScreenHeader;
	}


	public String getProfileScreenHeaderString() {
		return profileScreenHeaderString;
	}


	public By getProfilePic() {
		return profilePic;
	}


	public By getUserNameBy() {
		return userNameBy;
	}


	public By getOnHikeSince() {
		return onHikeSince;
	}


	public By getSendAMessage() {
		return sendAMessage;
	}


	public By getAddAsFavorite() {
		return addAsFavorite;
	}


	public By getAddAsFavoriteStatus() {
		return addAsFavoriteStatus;
	}

	
	//public methods 
	
	public ChatThreadScreen goBack() {
		clickOnElement(backButtonProfileScreen);
		return new ChatThreadScreen(userName);
	}
	
	public ChatThreadScreen clickOnSendAMessage() {
		
		//return chat thread screen object
		//clickOnElement(getSendAMessage());
		try {
			WebElement sendAMessageElement = driver.findElement(sendAMessage);
			new TouchAction(driver).press(sendAMessageElement).perform();
		} catch(Exception e) {
			
		}
		return new ChatThreadScreen(userName);
	}


	public void checkFavoriteOrNot() {
		
		//if not fav, make it non fav
		boolean favorite = isElementPresent(addAsFavorite);
		if(favorite) {
			//not favorite. add as favorite and check if text changes
			Assert.assertTrue(getTextByName(addAsFavoriteStatus).contains("to favorites now."), "The favorite status text is incorrect.");
			clickOnElement(addAsFavorite);
		}
		//Assert.assertTrue(getTextByName(addAsFavoriteStatus).contains("accept your request."), "The favorite status text is incorrect.");
	}
	

}
