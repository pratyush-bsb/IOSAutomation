package com.bsb.hike.ios.screens.interfaces;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

public interface ContactSelectionInterface {

	By favoritesTab = MobileBy.name("FAVORITES");
	By peopleOnHikeTab = MobileBy.name("PEOPLE ON HIKE");
	By smsContactsTab = MobileBy.name("SMS CONTACTS");
	By groupChatsTab = MobileBy.name("GROUP CHATS");
	String searchOrEnterNumberString = "Search or Enter Number";

	//to search for all tabs
	By allTabs = MobileBy.IosUIAutomation(".tableViews()[0].groups()");

	//to get number of contacts in each group -- condition that favorites exist
	String contactsCountPrefix = ".tableViews()[0].groups()[";
	String contactsCountSuffix = "].staticTexts()[1]";
	String nonHikeContactsCountSuffix = "].staticTexts()[0]";
	
	//this will return all contacts, fav or hike or sms 
	String allContactsPrefix = ".tableViews()[0].cells()";
	String firstContact = ".tableViews()[0].cells()[0]";
	String contactNameSuffix = ".staticTexts()[0]";
	String contactNumberSuffix = ".staticTexts()[1]";
	
	By forwardButton = MobileBy.name("Forward");
	By editAndForwardButton = MobileBy.name("Edit and Forward");
	
	By editForwardingMessageWindow = MobileBy.IosUIAutomation(".textViews()[0]");

	public void getCountOfContacts();
	
}
