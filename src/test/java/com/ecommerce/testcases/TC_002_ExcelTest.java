package com.ecommerce.testcases;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ecommerce.externaldatautilities.XLUtility;
import com.ecommerce.testcomponents.Base;

public class TC_002_ExcelTest extends Base{


	@DataProvider(name = "LoginData")
	public Object[][] getData() throws IOException
	{
	
		//get data from excel
		//path were the excel file is located
		String path = "src\\test\\java\\com\\ecommerce\\testdata\\externalexcel.xlsx";
		//usage of utility file (import + visibility public)
		XLUtility xlutil = new XLUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1");   //gets total rows
		int totalcols = xlutil.getCellCount("Sheet1",1); // gets total columns
		
		String loginData[][] = new String[totalrows][totalcols];
		
		for(int i =1;i<=totalrows;i++)
		{
			for(int j =0;j<totalcols;j++)
			{
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j); //we are ignoring the header parts 
			} //index at array//i=0,j=0                   //i=1, j=0 (index postion in XL)
		}
		
		return loginData;
	}

	@Test(dataProvider="LoginData")
public void LoginErrorValidation(String email,String password) {

		LOGGER.info("******login data from excel*****");
		landingPage.loginApplication(email, password);

}
}

