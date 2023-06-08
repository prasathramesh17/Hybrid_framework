package com.ecommerce.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.testcomponents.Base;

public class TC_001_LoginTest extends Base {

	@Test
	public void loginValidation() throws InterruptedException {

		LOGGER.info("******login validation test with valid data*****");
		landingPage.loginApplication("automation2023@gmail.com", "P@vilion5");
		LOGGER.info("******entered valid credentials*****");
		if (driver.getTitle().equals("Let's Shop")) {
			
			Assert.assertTrue(true);
			LOGGER.info("******title matched*****");
				
		} else {
			Assert.assertTrue(false);
			LOGGER.info("****** title not matched*****");
		}
}

	@Test
	public void loginErrorValidate() throws InterruptedException {

		LOGGER.info("******login error validation invalid data*****");
		landingPage.loginApplication("automation2023@gmail.com", "Pavilion5");
		LOGGER.info("******entered invalid credentials*****");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		LOGGER.info("****** error msg displayed*****");
		System.out.println(landingPage.getErrorMessage());

	}
}
