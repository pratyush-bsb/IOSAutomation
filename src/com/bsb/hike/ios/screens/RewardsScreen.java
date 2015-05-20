package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class RewardsScreen extends HikeLibrary{

	public RewardsScreen() {
		waitForRewardsPageToLoad();
	}

	private void waitForRewardsPageToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 5) {
			try {
				int countOfRewards = driver.findElements(allRewards).size();
				if(countOfRewards > 0) {
					pageLoaded = true;
				}
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	//protected identifiers
	protected By allRewards = MobileBy.IosUIAutomation(".scrollViews()[0].webViews()[0].links()");
	protected By particularRewardSuffix = MobileBy.IosUIAutomation(".links()[0]");
	protected By RewardHeader = MobileBy.IosUIAutomation(".scrollViews()[0].webViews()[0].staticTexts()[0]");
	protected By activateNatashaBotButton = MobileBy.name("Hi, Natasha!");

	public void activateNatashaBot() {

		List<WebElement> allRewardElements = driver.findElements(allRewards);

		//iterate over all rewards and click on natasha bot reward
		for(WebElement eachBot : allRewardElements) {
			String reward = eachBot.getAttribute("name");
			if(reward.contains("natasha on hike")) {
				//found natasha bot. click on natasha bot.
				WebElement childBotElement = eachBot.findElement(particularRewardSuffix);
				childBotElement.click();
				waitForNatashaPageToLoad();
				clickOnElement(activateNatashaBotButton);
			}
		}
	}

	private void waitForNatashaPageToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(activateNatashaBotButton);
				pageLoaded = true;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

}
