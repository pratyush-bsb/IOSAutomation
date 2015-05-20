package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class NewGroupScreen extends HikeLibrary {
	
	public NewGroupScreen() {
		waitForNewGroupScreenToLoad();
	}

	private void waitForNewGroupScreenToLoad() {
		
		boolean pageLoaded = false;
		int counter = 0; 
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(getAddPhoto());
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}
	
	//identifiers
	
	protected By backButton = MobileBy.name("All Conversations");
	protected By screenHeader = MobileBy.name("New Group");
	protected By nextButton = MobileBy.name("Next");
	protected By addPhoto = MobileBy.name("add photo");
	protected By newGroupInfo = MobileBy.name("Chat with up to 500 friends");
	protected String newGroupInfoString = "Group chat with";
	protected By typeGroupName = MobileBy.IosUIAutomation(".textFields()[0]"); //get text by value
	protected String defaultGroupNameText = "Name the Group";
	
	//on clicking on add photo
	protected By takePhotoButton = MobileBy.name("Take Photo");
	protected By chooseExistingButton = MobileBy.name("Choose Existing");
	protected By cancelButton = MobileBy.name("Cancel");
	
	//public getters
	public By getBackButton() {
		return backButton;
	}

	public By getScreenHeader() {
		return screenHeader;
	}

	public By getNextButton() {
		return nextButton;
	}

	public By getAddPhoto() {
		return addPhoto;
	}

	public By getNewGroupInfo() {
		return newGroupInfo;
	}

	public String getNewGroupInfoString() {
		return newGroupInfoString;
	}

	public By getTypeGroupName() {
		return typeGroupName;
	}

	public String getDefaultGroupNameText() {
		return defaultGroupNameText;
	}

	public By getTakePhotoButton() {
		return takePhotoButton;
	}

	public By getChooseExistingButton() {
		return chooseExistingButton;
	}

	public By getCancelButton() {
		return cancelButton;
	}

	public String getDefaultGroupText() {
		return defaultGroupNameText;
	}
	
	//public methods
	public void typeGroupName(String groupName) {
		
		enterTextWithClear(typeGroupName, groupName);
	}
	
	public void clickOnAddPhoto() {
		clickOnElement(addPhoto);
	}
	
	public void cancelAddingPhoto() {
		clickOnElement(cancelButton);
	}

	public GroupContactSelectionScreen clickOnNextButton() {
		
		clickOnElement(nextButton);
		return new GroupContactSelectionScreen();
	}
	
	

}
