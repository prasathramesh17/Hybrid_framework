package com.ecommerce.testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ecommerce.pageobjects.CartPage;
import com.ecommerce.pageobjects.CheckoutPage;
import com.ecommerce.pageobjects.ConfirmationPage;
import com.ecommerce.pageobjects.OrderPage;
import com.ecommerce.pageobjects.ProductCatalogue;
import com.ecommerce.testcomponents.Base;

public class TC_003_JsonTest extends Base {

	String productName = "ADIDAS ORIGINAL";

	@Test(dataProvider = "getData")
	
	public void submitOrder(HashMap<String,String>input)
			throws InterruptedException, IOException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));

		LOGGER.info("******user enterde the credential login success*****");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		LOGGER.info("******product added success*****");
		CartPage cartPage = productCatalogue.goToCartPage();
		LOGGER.info("******entered cart page success*****");
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		LOGGER.info("*****item matched with cart page success*****");
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");

		ConfirmationPage confirmPage = checkoutPage.submitOrder();

		
		String confirmMessage = confirmPage.getConfirmationMessage();
	
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		
	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistoryTest() throws InterruptedException {

		// ADIDAS ORIGINAL
		LOGGER.info("******Order history test method*****");
		ProductCatalogue productCatalogue = landingPage.loginApplication("automation2023@gmail.com",
				"P@vilion5");
		LOGGER.info("******loged in order history with valid data*****");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		LOGGER.info("******view the order from order page*****");
		
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
		LOGGER.info("******verified order is matched*****");
		
	}

	
	@DataProvider
	public Object[][] getData() throws IOException
	{

		
		List<HashMap<String, String>> data = getJsonDataToMap
				(System.getProperty("user.dir")+"//src//test//java//com//ecommerce//testdata//purchaseorder.json");
		
		return new Object[][]  {{data.get(0)}, {data.get(1)} };
		
	}
}

