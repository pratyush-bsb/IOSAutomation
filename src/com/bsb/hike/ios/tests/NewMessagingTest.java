package com.bsb.hike.ios.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.FavoritesScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.bsb.hike.ios.screens.UserProfileScreen;

public class NewMessagingTest extends HikeLibrary {

	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
		//driver.launchApp();
	}

	@AfterClass
	public void tearDown() throws Exception{
		driver.quit();

	}

	@Test(priority=1)
	public void test001_AssertingHomeScreenButtons() {
		ignoreLoveUsingHikePopup();
		Reporter.log(iOSAutomation_DESCRIPTION+" : Launch app\n"+
				" 1. Start a new chat icon should be present\n"+
				" 2. Overflow icon should be present.\n" +
				" 3. Hidden chat icon should be present.\n" +
				" 4. Compose timeline should be present.\n");

		//ensure that at home page. If not, go to home page
		goToHome();

		//assertions
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getConversationCompose()), "Chat icon is not present");
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getOverflow_Option()), "Menu Icon is not present");
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getConversationHide()), "Hidden Chat Icon is not present");
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getConversationTimeline()), "Compose Timeline Icon is not present");

		goToHome();
	}

	@Test(priority=2)
	public void test002_AssertingStartAChat() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : Tap on the new chat icon\n"+
				" 1. A new chat window should open up with title 'Start a chat' \n"+
				" 2. Cursor focus should not be on the search field. \n"+
				" 3. Keyboard should not be enabled by default. \n"+
				" 4. All the contacts in the addressbook should be listed. \n");

		//click on new chat and observe screen for validity of all elements
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();

		//assert for texts of start a chat
		Assert.assertTrue(isElementPresent(startNewChatObj.getStartAChatBackButton()), "Back button is not present");
		Assert.assertEquals(startNewChatObj.getStartAChatHeaderString(), getTextByName(startNewChatObj.getStartAChatHeader()));
		Assert.assertEquals(getTextByValue(startNewChatObj.getSearchOrEnterNumber()), startNewChatObj.getSearchOrEnterNumberString());

		//assert that all tabs are available
		//Assert.assertTrue(isElementPresent(startNewChatObj.getFavoritesTab()), "Favorites tab is not present");
		Assert.assertTrue(isElementPresent(startNewChatObj.getPeopleOnHikeTab()), "People on hike tab is not present");
		Assert.assertTrue(isElementPresent(startNewChatObj.getSmsContactsTab()), "SMS contacts tab is not present");
		goToHome();
	}

	@Test(priority=3)
	public void test003_AssertingANewChat() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on the compose new message icon \n" +
				"2. Search field with enter a name or number. \n" +
				"3. Tap on Search field. \n" +
				"4. Pick First User available. \n" +
				"5. Assert all the elements on the chat thread screen.\n");

		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		//TODO no identifier for logo next to fav / hike user / sms contacts

		startNewChatObj.clickOnSearchTab();
		waitForKeyboardToLoad();

		//assert that keyboard has loaded
		Assert.assertTrue(isKeyboardVisible(), "Keyboard did not appear after clicking on search for a contact field");
		startNewChatObj.cancelTypingForContact();

		//get count of contacts in different categories
		startNewChatObj.getCountOfContacts();

		//select a contact and assert that chat started with selected contact
		ChatThreadScreen chatThreadScreenObj = startNewChatObj.clickOnFirstContact();
		Assert.assertEquals(chatThreadScreenObj.getUserName(), getTextByName(chatThreadScreenObj.getChatThreadHeader()), "Chat did not start with the selected user");

		//assert items on chat thread page
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getChatThreadScreenBackButton()), "Back button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getChatThreadOverflowButton()), "Overflow button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getStickerButton()), "Sticker button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getEmojiButton()), "Emoji button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getAttachmentButton()), "Attachment button not available");
		Assert.assertEquals(getTextByName(chatThreadScreenObj.getInitialTypeBoxMessage()), chatThreadScreenObj.getInitialTypeBoxMessageString(), "Initial message does not match with 'Hike message...'");

		//click on overflow button and check for remaining buttons
		chatThreadScreenObj.clickOnOverflowButton();
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getProfileButton()), "Profile button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getCallButton()), "Call button not available");
		Assert.assertTrue(isElementPresent(chatThreadScreenObj.getThemeButton()), "Theme button not available");
		if(!isElementPresent(chatThreadScreenObj.getMoreButton())) {
			Reporter.log("More button not found inside chat thread because no messages in chat.");
		}
		
	}

	@Test(priority=4)
	public void test004_AssertNewChatSearchCases() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Search for a MSISDN or contact that does not exist. \n" +
				" 2. Search for a contact by typing MSISDN of the contact. \n" +
				"3. Search for a contact by typing name of the contact. \n");
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();

		//check for a non hike user
		startNewChatObj.assertSelectedUserValidity(HIKE_SMS_CONTACT_NAME_1, "free");
		
		//check for a non hike user using name. 'No Results Found' should come
		goToHome();
		startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		startNewChatObj.clickOnSearchTab();
		enterText(startNewChatObj.getSearchOrEnterNumber(), "sdfasldjfweahasdknaskdjfoiaeroajsfklasf");
		Assert.assertTrue(isElementPresent(startNewChatObj.getNoResultsBy()), "No Results tab did not come when searched for unsaved user");
		//driver.hideKeyboard();
		hideHikeKeyboard();
		//check for a saved hike user. Searched by name
		goToHome();
		startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		startNewChatObj.assertSelectedUserValidity(HIKE_CONTACT_NAME, "hike");

		//check for a saved hike user. Searched by MSIDN
		goToHome();
		startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		startNewChatObj.assertSelectedUserValidity(HIKE_NOT_SAVED_USER, "hike");
		
	}
	
	@Test(priority=5)
	public void test005_FavoriteNotFavoriteUserValidity() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. If no user is marked as favorites. \n" +
				"2. If user is marked as favorites. \n" +
				"3. Check for count if any favorite user, else absence of favorite tab if none.\n");
		
		goToHome();
		homeScreenMenuObj.clickOnOverflow();
		FavoritesScreen favoriteScreenObj = homeScreenMenuObj.clickOnFavorites_Lbl();
		
		//assert items on favorites screen
		
		
		//check if any favorites. if count is 0, then no favorites
		int countOfFavoriteContacts = Integer.parseInt(getTextByName(favoriteScreenObj.getFavoriteContactsCount()));
		int countOfHikeUsers = Integer.parseInt(getTextByName(favoriteScreenObj.getPeopleOnHikeContactsCount()));
		
		if (countOfFavoriteContacts == 0) {
			//no favorite contacts, check if 'add favorites' available then add user to favorite and check back

			Assert.assertEquals(favoriteScreenObj.getAddFavoritesText(), getTextByName(favoriteScreenObj.getAddFavoritesBy()), "Add Favorites text not available with 0 favorite contacts");
			favoriteScreenObj.addUserAsFavorite();
			
			//count of favorite should increase to 1 and hike users should decrease by 1
			Assert.assertEquals(getTextByName(favoriteScreenObj.getFavoriteContactsCount()), "1", "Count of favorites did not increase after adding contact as favorite");
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getPeopleOnHikeContactsCount())), (countOfHikeUsers-1), "Count of hike users did not decrease after adding contact as favorite");
			
			//now remove contact from favorites and check for validity
			favoriteScreenObj.removeUserFromFavorites();
			//assert presence of remove button
			Assert.assertTrue(isElementPresent(favoriteScreenObj.getRemoveButton()), "Remove button did not appear on swiping over user in favorites");
			clickOnElement(favoriteScreenObj.getRemoveButton());
			Assert.assertEquals(favoriteScreenObj.getAddFavoritesText(), getTextByName(favoriteScreenObj.getAddFavoritesBy()), "Add Favorites text not available with 0 favorite contacts");
			Assert.assertEquals(getTextByName(favoriteScreenObj.getFavoriteContactsCount()), "0", "Count of favorites did not decrease after removing contact as favorite");
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getPeopleOnHikeContactsCount())), (countOfHikeUsers), "Count of hike users did not increase after adding contact as favorite");
		} else {
			//favorite contact is there, remove and check count
			favoriteScreenObj.removeUserFromFavorites();
			//assert presence of remove button
			Assert.assertTrue(isElementPresent(favoriteScreenObj.getRemoveButton()), "Remove button did not appear on swiping over user in favorites");
			clickOnElement(favoriteScreenObj.getRemoveButton());
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getFavoriteContactsCount())), countOfFavoriteContacts-1, "Count of favorites did not decrease after removing contact as favorite");
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getPeopleOnHikeContactsCount())), (countOfHikeUsers+1), "Count of hike users did not increase after adding contact as favorite");
			
			//now add back user to fav and check validity
			favoriteScreenObj.addUserAsFavorite();
			
			//count of favorite should increase to 1 and hike users should decrease by 1
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getFavoriteContactsCount())), countOfFavoriteContacts, "Count of favorites did not increase after adding contact as favorite");
			Assert.assertEquals(Integer.parseInt(getTextByName(favoriteScreenObj.getPeopleOnHikeContactsCount())), countOfHikeUsers, "Count of hike users did not decrease after adding contact as favorite");
			//TODO add 2-way fav authentication
			
		}
		
	}
	
	@Test(priority=6)
	public void test006_validateUserProfilePage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Tap on user head should take to profile page. \n" +
				"2. Assert all elements on the page. \n" +
				"3. tap on send a message should take to chat thread with same user.\n");
		
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.clickOnFirstContact();
		String user = chatThreadObj.getUserName();
		Assert.assertEquals(getTextByName(chatThreadObj.getChatThreadHeader()), user, "Chat did not start with the same user");
		UserProfileScreen userProfileObj = chatThreadObj.clickOnUserName();
		Assert.assertEquals(getTextByName(userProfileObj.getUserNameBy()), user, "Profile did not load for the same user");
		
		//assert all elements on profile page
		Assert.assertTrue(isElementPresent(userProfileObj.getBackButtonProfileScreen()), "Back button not available in user profile page");
		Assert.assertTrue(isElementPresent(userProfileObj.getProfileScreenHeader()), "Profile page header came other than Profile");
		Assert.assertTrue(getTextByName(userProfileObj.getOnHikeSince()).contains("on hike since"), "On hike since tab did not appear");
		Assert.assertTrue(isElementPresent(userProfileObj.getSendAMessage()), "Send a message button did not appear");
		
		//check if fav/not fav. If not fav, make it fav
		userProfileObj.checkFavoriteOrNot();
		
		//click on send message should take to chat thread
		chatThreadObj = userProfileObj.clickOnSendAMessage();
		Assert.assertEquals(getTextByName(chatThreadObj.getChatThreadHeader()), user, "Send a message button did not take chat to same user");
	}
	
	@Test(priority=7)
	public void test007_contactWithExistingChat() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Tap on new chat icon. \n" +
				"2. Select a contact with whom chat exists. \n" +
				"3. Ensure that the existing chat thread opens.\n");
		
		String textToAssert = "This is a random text to assert.";
		String existingUser = "";
		
		goToHome();
		//ensure if any existing chats
		/*int existingChats = driver.findElements(homeScreenMenuObj.getAllExistingChats()).size();
		if(existingChats > 0) {
			//chats exists. //TODO Ensure that the first chat is not natasha bot
			existingUser = getTextByName(homeScreenMenuObj.getFirstExistingChatName());
			ChatThreadScreen chatThreadObj = homeScreenMenuObj.clickOnFirstChat();
			//ensure that chat started with same user
			if(chatThreadObj.getUserName().equalsIgnoreCase(existingUser)) {
				//same user. send a message to check for later
				chatThreadObj.sendMessage(textToAssert);
			}
		}*/
		goToHome();
		//start chat with some user and open and check again
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.searchContactByNameAndStartChat(HIKE_CONTACT_NAME);
		chatThreadObj.sendMessage(textToAssert);
		existingUser = chatThreadObj.getUserName();
		
		goToHome();
		
		//start chat through new chat and ensure that same user opened
		startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		chatThreadObj = startNewChatObj.searchContactByNameAndStartChat(existingUser);
		String textCapturedLater = chatThreadObj.getLastMessage();
		
		Assert.assertEquals(textToAssert, textCapturedLater, "Chat started with the same existing window.");
	}

}
