package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ProfileScreenInterface;

public class GroupProfileScreen extends HikeLibrary implements ProfileScreenInterface {

	public GroupProfileScreen (String groupName) {
		this.groupName = groupName;
		waitForGroupProfileScreenToLoad();
	}

	private void waitForGroupProfileScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				String chatLoaded = getTextByName(profileScreenHeader);
				pageLoaded = chatLoaded.equalsIgnoreCase(profileScreenHeaderString);
			} catch(Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) {}
			}
		}
	}

	protected String groupName;
	String profileScreenHeaderString = "Group Info";
	protected By profileScreenHeader = MobileBy.name("Group Info");
	protected By profilePic = MobileBy.name("groupAvatarPlaceholderMedium0");
	protected By addMemberButton = MobileBy.name("Add Members");
	protected By leaveGroupButton = MobileBy.name("Leave Group");
	protected By editButton = MobileBy.name("ic edit banner group info");
	protected By doneButton = MobileBy.name("Done");
	protected By allUsers = MobileBy.IosUIAutomation(".tableViews()[0].cells()");
	protected By eachUserSuffix = MobileBy.IosUIAutomation(".staticTexts()[0]");
	protected By eachUserDesignationSuffix = MobileBy.IosUIAutomation(".staticTexts()[1]");
	protected By groupEditField = MobileBy.IosUIAutomation(".tableViews()[0].textFields()[0]");
	protected By cancelButton = MobileBy.name("Cancel");
	protected By addToContacts = MobileBy.name("Add to Contacts");
	protected By removeFromGroup = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[0]");
	protected By confirmRemoveFromGroup = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[0].buttons()[0]");
	protected By blockContact = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[2]");
	protected By cameraIcon = MobileBy.name("largeCamera");
	protected By takePhotoButton = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[0].buttons()[0]");
	protected By chooseExistingButton = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[1].buttons()[0]");
	protected By groupNameBy = MobileBy.IosUIAutomation(".tableViews()[0].textFields()[0]"); //get text by value

	//public getters
	public String getGroupName() {
		return groupName;
	}

	public By getTakePhotoButton() {
		return takePhotoButton;
	}

	public By getCameraIcon() {
		return cameraIcon;
	}

	public By getDoneButton() {
		return doneButton;
	}

	public By getAllUsers() {
		return allUsers;
	}

	public By getCancelButton() {
		return cancelButton;
	}

	public By getAddToContacts() {
		return addToContacts;
	}

	public By getRemoveFromGroup() {
		return removeFromGroup;
	}

	public By getBlockContact() {
		return blockContact;
	}

	public By getEachUserDesignationSuffix() {
		return eachUserDesignationSuffix;
	}

	public By getEachUserSuffix() {
		return eachUserSuffix;
	}

	public By getGroupEditField() {
		return groupEditField;
	}

	public String getProfileScreenHeaderString() {
		return profileScreenHeaderString;
	}

	public By getProfileScreenHeader() {
		return profileScreenHeader;
	}

	public By getProfilePic() {
		return profilePic;
	}

	public By getAddMemberButton() {
		return addMemberButton;
	}

	public By getLeaveGroupButton() {
		return leaveGroupButton;
	}

	public By getEditButton() {
		return editButton;
	}

	public By getInviteToHikeButton() {
		return inviteToHike;
	}
	//public methods

	public void editGroupName(String newGroupName) {

		clickOnElement(getEditButton());
		waitForKeyboardToLoad();
		/*for(int i=0; i < this.groupName.length(); i++) {
			performPartialDelete();
		}*/
		enterTextWithClear(groupEditField, newGroupName);
		clickOnElement(doneButton);

	}

	public void clickOnInviteToHike() {
		clickOnElement(inviteToHike);
	}

	public void clickOnLeaveButton() {
		clickOnElement(leaveGroupButton);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public GroupContactSelectionScreen addMember() {
		clickOnElement(addMemberButton);
		return new GroupContactSelectionScreen();
	}

	public void leaveGroup() {
		clickOnElement(leaveGroupButton);
		clickOnElement(leaveGroupButton);
	}

	public List<String> getListOfAllUsers() {

		List<String> allUserNames = new ArrayList<String>();
		try {
			List<WebElement> allUsersElements = driver.findElements(allUsers);

			//iterate over all user elements and fetch their names
			for(WebElement eachUser : allUsersElements) {
				WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String user = eachUserNameElement.getAttribute("name");
				allUserNames.add(user);
			}
		} catch(Exception e) {

		}
		return allUserNames;
	}

	public List<WebElement> getElementsOfAllUsers() {
		List<WebElement> allUserElements = new ArrayList<WebElement>();
		try {
			allUserElements = driver.findElements(allUsers);

		} catch(Exception e) {}
		return allUserElements;
	}

	public GroupThreadScreen goBackToThread() {
		clickOnElement(backButtonProfileScreen);
		String groupName = getTextByValue(groupNameBy);
		return new GroupThreadScreen(groupName);
	}

	public PhotosScreen clickOnCameraIcon() {
		try {
			WebElement cameraButton = driver.findElement(cameraIcon);
			new TouchAction(driver).press(cameraButton).perform();
		} catch(Exception e) {
			Reporter.log("Not able to click on change image in group info screen");
		}
		return new PhotosScreen();
	}

	public CameraScreen takePhoto() {
		clickOnElement(takePhotoButton);
		return new CameraScreen();
	}

	public void removeUser(String userToRemove) {

		List<WebElement> allUsersElements = getElementsOfAllUsers();

		//iterate all users and check for name;
		for(WebElement eachUser : allUsersElements) {
			try {
				WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String user = eachUserNameElement.getAttribute("name");
				if(userToRemove.equalsIgnoreCase(user)) {
					longPressByElement(eachUser);
					clickOnElement(removeFromGroup);
					clickOnElement(confirmRemoveFromGroup);
				}
			} catch(Exception e){}
		}
	}

	public boolean participantRemovesUser(String userToRemove) {

		boolean removed = false;

		List<WebElement> allUsersElements = getElementsOfAllUsers();

		//iterate all users and check for name;
		for(WebElement eachUser : allUsersElements) {
			try {
				WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String user = eachUserNameElement.getAttribute("name");
				if(userToRemove.equalsIgnoreCase(user)) {
					new TouchAction(driver).longPress(eachUserNameElement).perform();
					removed = true;
				}
			} catch(Exception e){
				removed = false;
				break;
			}
		}

		return removed;
	}

	public GroupThreadScreen removeAllUsers(String groupName) {

		List<WebElement> allUsersElements = getElementsOfAllUsers();

		//iterate all users and check for name;
		for(WebElement eachUser : allUsersElements) {
			try {
				longPressByElement(eachUser);
				if(isElementPresent(removeFromGroup)){
					clickOnElement(removeFromGroup);
					clickOnElement(confirmRemoveFromGroup);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return new GroupThreadScreen(groupName);
	}

	//returns true if particular type of user available, as passed in @param usertype
	public boolean verifyPresenceOfParticularUserType(String userType) {

		boolean userTypeFound = false;

		List<WebElement> allUsersFound = getElementsOfAllUsers();

		for(WebElement eachUser : allUsersFound) {
			try {
				WebElement eachUserNameElement = eachUser.findElement(eachUserDesignationSuffix);
				String userDesignation = eachUserNameElement.getAttribute("name");
				if(userType.equalsIgnoreCase(userDesignation)) {
					//user type found
					userTypeFound = true;
					break;
				}
			} catch(Exception e) {
				userTypeFound = false;
			}
		}
		return userTypeFound;
	}

	//returns true if particular type of user available, as passed in @param usertype
	public boolean verifyPresenceOfParticularUser(String user) {

		boolean userTypeFound = false;

		List<WebElement> allUsersFound = getElementsOfAllUsers();

		for(WebElement eachUser : allUsersFound) {
			try {
				//WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String userName = eachUser.getAttribute("name");
				if(user.equalsIgnoreCase(userName)) {
					//user type found
					userTypeFound = true;
					break;
				}
			} catch(Exception e) {
				userTypeFound = false;
			}
		}
		return userTypeFound;
	}

	public String getUserTypeForUser(String user) {

		String userType = "";

		List<WebElement> allUsersFound = getElementsOfAllUsers();

		for(WebElement eachUser : allUsersFound) {
			try {
				//WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String userName = eachUser.getAttribute("name");
				driver.scrollTo(userName);
				if(user.equalsIgnoreCase(userName)) {
					//user type found
					WebElement eachUserDesignationElement = eachUser.findElement(eachUserDesignationSuffix);
					userType = eachUserDesignationElement.getAttribute("name");
					break;
				}
			} catch(Exception e) {

			}
		}
		return userType;
	}

	public AddToContacts addUnsavedToContacts(String contactToSave) {

		List<WebElement> allUsersFound = getElementsOfAllUsers();
		AddToContacts addToContactsObj = null;

		for(WebElement eachUser : allUsersFound) {
			try {
				//WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String userName = eachUser.getAttribute("name");
				if(contactToSave.equalsIgnoreCase(userName)) {
					driver.scrollTo(userName);
					longPressByElement(eachUser);
					clickOnElement(addToContacts);
					clickOnElement(addToContacts);
					addToContactsObj = new AddToContacts();
				}
			} catch(Exception e) {

			}
		}
		return addToContactsObj;
	}

	public UserProfileScreen goToUserProfile (String user) {

		UserProfileScreen userProfileScreen = null;

		List<WebElement> allUsersFound = getElementsOfAllUsers();

		for(WebElement eachUser : allUsersFound) {
			try {
				//WebElement eachUserNameElement = eachUser.findElement(eachUserSuffix);
				String userName = eachUser.getAttribute("name");
				if(user.equalsIgnoreCase(userName)) {
					//user found. Tap on the element and return
					driver.scrollTo(userName);
					eachUser.click();
					userProfileScreen = new UserProfileScreen(user);
				}
			} catch(Exception e) {}
		}

		return userProfileScreen;
	}

}
