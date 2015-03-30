package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.library.HikeLibrary;

public class ChatThreadScreen extends AppiumLibrary{

	public static By chat_navbar_overflow_menu_Btn=By.name("chat navbar overflow menu");
	public static By Hike_message_Txt=By.name("Hike message...");
	public static By chat_bottombar_emoji_Btn=By.name("chat bottombar emoji");
	public static By chat_bottombar_attachment_Btn=By.name("chat bottombar attachment");
	public static By userName_Lbl=By.name(HikeLibrary.DEFAULT_MSISDN);
	
	
	public static boolean Hike_message_Txt_IsExist() throws InterruptedException{
		return isElementPresent(Hike_message_Txt);
	}
	public static boolean chat_navbar_overflow_menu_Btn_IsExist() throws InterruptedException{
		return isElementPresent(chat_navbar_overflow_menu_Btn);
	}
	public static boolean chat_bottombar_emoji_Btn_IsExist() throws InterruptedException{
		return isElementPresent(chat_bottombar_emoji_Btn);
	}
	public static boolean chat_bottombar_attachment_Btn_IsExist() throws InterruptedException{
		return isElementPresent(chat_bottombar_attachment_Btn);
	}	
	public static boolean userName_Lbl_IsExist() throws InterruptedException{
		return isElementPresent(userName_Lbl);	
	}

//	public static String userNameValidation(){
//		System.out.println("daaaasddd");
//		String s = getText(userName_Lbl);
//		System.out.println("s"+s);
//		return s;
//	}
}
