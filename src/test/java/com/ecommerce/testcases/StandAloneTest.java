package com.ecommerce.testcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// belong to landpage
		// reach->url
		driver.get("https://rahulshettyacademy.com/client");
		// Enter->login credentials + login btn click
		driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
		driver.findElement(By.id("login")).click();

		// belong to product page
		// wait till new product page appears after successful login + wait till
		// elements visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		// select->entire product available in product page
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		// within-> products-> filter-> req product->gettext->equals->productname
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		// after finding by text-> locate near add to cart-> within product-> click
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		// wait till product added to cart appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// ng-animating
		// wait till product added to cart disappears
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		// click on cart header
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		// belongs to cart page
		// within cart page-> select all -> anymatch->with selected product-> verify
		// product
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		// verify product
		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		// click-> checkout
		driver.findElement(By.cssSelector(".totalRow button")).click();
		// checkout page
		// fill auto suggestion dropdown-> Actions
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		// wait till entered text appear/visible->
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		// after appeared -> click india
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		// then click-> place order btn
		driver.findElement(By.cssSelector(".action__submit")).click();

		// confirmation page
		// verify thankyou msg -> so that order confirmation can be made
		// get the text-> verify what should be confirm msg when order is placed
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();

	}

}
