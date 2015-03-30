package com.bsb.hike.ios.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreen;
import com.bsb.hike.ios.screens.NewChatContactSelectScreen;

public class NewMessagingTest extends AppiumLibrary{

	AppiumCapabilities appium = new AppiumCapabilities();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
	}


	public void test001PreviewHomeScreen() throws InterruptedException{	
		Thread.sleep(1000);
		Assert.assertTrue(HomeScreen.checkPresent_conversation_compose_BTN(),"Compose home icon is not present");
		Assert.assertTrue(HomeScreen.checkPresent_conversation_timeline_BTN(),"Timeline home icon is not present");
		Assert.assertTrue(HomeScreen.checkPresent_no_threads_sticker_icon_Sticker(),"No chat screen is not present");
	}

	public void test002VerifySelectContact() throws InterruptedException{
		String num="77";
		try {
			HomeScreen.clickOnconversation_compose_BTN();
			Thread.sleep(1000);
			Assert.assertTrue(NewChatContactSelectScreen.Select_a_Contact_Txt_IsExist(),"Select a contact header text not present");
			Assert.assertTrue(NewChatContactSelectScreen.PEOPLE_ON_HIKE_Txt_IsExist(),"People on hike text is not present");
			Assert.assertTrue(NewChatContactSelectScreen.Search_Bar_IsExist(),"Search bar is not present");			
			NewChatContactSelectScreen.clickOnSearch_Bar();
			NewChatContactSelectScreen.clickOnCancel_Btn();
			NewChatContactSelectScreen.clickOnSearch_Bar();
			NewChatContactSelectScreen.setSelfChat(num);
			Thread.sleep(1000);
			Assert.assertTrue(NewChatContactSelectScreen.Clear_text_Btn_IsExist(),"Clear text button is not present after number is entered");
			Assert.assertTrue(NewChatContactSelectScreen.Tap_to_chat_Txt_IsExist(),"Tap to chat text is not present after number is entered");
			NewChatContactSelectScreen.clickOnClear_text_Btn();
			Thread.sleep(2000);

		} catch (Exception e) {
			System.out.println("Case failed");
		}
	}

	@Test
	public void test003CreateChat(){
		try {
			HomeScreen.clickOnconversation_compose_BTN();
			NewChatContactSelectScreen.clickOnSearch_Bar();
			HikeLibrary.setDEFAULT_MSISDN();
			NewChatContactSelectScreen.setSelfChat(HikeLibrary.getDEFAULT_MSISDN_Create());
			Thread.sleep(1000);
			NewChatContactSelectScreen.clickOnTap_to_chat_Txt();
			Thread.sleep(1000);
			//Assert.assertEquals(ChatThreadScreen.userNameValidation(), HikeLibrary.HIKE_CONTACT_NAME_1);
			//			Assert.assertTrue(ChatThreadScreen.chat_navbar_overflow_menu_Btn_IsExist(),"Chat screen is not present");
			//		Assert.assertTrue(ChatThreadScreen.userName_Lbl_IsExist(),"Username is not present on chat screen");
			//			Assert.assertTrue(ChatThreadScreen.Hike_message_Txt_IsExist(),"Hike message default text is not present for self hike user");
			//			Assert.assertTrue(ChatThreadScreen.chat_bottombar_emoji_Btn_IsExist(),"Emoji button is not present");
			//			Assert.assertTrue(ChatThreadScreen.chat_bottombar_attachment_Btn_IsExist(),"By default attachment button is not present");
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}



}
