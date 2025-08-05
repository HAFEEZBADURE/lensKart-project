package com.w2a.testcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class EyeglassesTest extends TestBase {

	@Test
	public void navigateToRectangleFrames() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Actions actions = new Actions(driver);

		// Step 1: Wait and hover on "Eyeglasses"
		WebElement eyeglasses = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("Eyeglasses"))));
		actions.moveToElement(eyeglasses).pause(Duration.ofSeconds(1)).perform();

		// Step 2: Wait and hover on "Men"
		WebElement menOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("Menbtn"))));
		actions.moveToElement(menOption).pause(Duration.ofSeconds(1)).perform();

		// Step 3: Wait and click on "Rectangle Frames"
		WebElement rectangleFrames = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("rectangleframe"))));
		rectangleFrames.click();

		// Optional wait to observe result
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement rectangleFilter = driver.findElement(By.xpath(OR.getProperty("rectanglebtn")));
		rectangleFilter.click();
		Thread.sleep(5000);

		// Wait until product containers are visible
		List<WebElement> products = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.cssSelector(OR.getProperty("ProductDetailsContainer"))));

		int count = Math.min(products.size(), 10);

		for (int i = 0; i < count; i++) {
			WebElement product = products.get(i);

			// Extract product name
			String name = product.findElement(By.cssSelector(OR.getProperty("productname"))).getText();

			// Extract product price
			String price = product.findElement(By.cssSelector(OR.getProperty("productprice"))).getText();
			System.out.println((i + 1) + ". " + name + " - " + price);
		}

		Thread.sleep(3000);
		// Step 1: Locate the dropdown element
		WebElement sortByDropdown = driver.findElement(By.id("sortByDropdown"));

		Select sortDropdown = new Select(sortByDropdown);
		sortDropdown.selectByValue("low_price");
		System.out.println("Selected 'Price: Low to High' option from dropdown");

		Thread.sleep(3000);
		System.out.println("Sorting by lowest price completed");
		
	}
}
