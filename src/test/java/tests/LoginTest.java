package tests;

import org.testng.annotations.Test;

import tests.DataFile;
import tests.Xls_Reader;


import tests.LoginPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class LoginTest {
	WebDriver driver;
	LoginPage lp = new LoginPage();
	
	DataFile dt=new DataFile();
	
	//Excel File
	

	@Test(priority=1, dataProvider="getData")
	public void loginWithWrongEmailPassword(String a, String b, String c) throws InterruptedException {
		lp.login(a,b);
		Thread.sleep(1000);
		String expected = c;
		String actual = lp.genError();
		Assert.assertEquals(actual, expected);

	}

	//@Test(priority=2)
	public void loginWithEmptyEmail() throws InterruptedException {

		lp.login(" ", dt.wrongPassword);
		Thread.sleep(1000);
		String expected = dt.uNameError;
		String actual = lp.readError();
		Assert.assertEquals(actual, expected);
	}

	//@Test(priority=3)
	public void loginWithEmptyPassword() throws InterruptedException {
		lp.login(dt.wrongEmail, " ");
		Thread.sleep(1000);
		String expected = dt.pError;
		String actual = lp.readError();
		Assert.assertEquals(actual, expected);

	}

	//@Test(priority=4)
	public void loginWithSpecialCharacter() throws InterruptedException {
		lp.login(dt.emailspecial, dt.wrongPassword);
		String expected = dt.sPCharError;
		String actual = lp.readError();
		Assert.assertEquals(actual, expected); // By.classname("Error__text).getText will also work to get text.

	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException, IOException {
		lp.openBrowser();
		lp.loginPage();

	}

	@AfterMethod
	public void afterMethod() throws InterruptedException {

		lp.closeBrowser();

	}
	@DataProvider
	public Object[][] getData() {
		// row stands for how many different data types test shoul run
		// coloumn stand for how many values per test
		Object[][] data = new Object[4][3];// here the size start from 1. its the size. 1,2,3,4
		// 0th row
		data[0][0] = dt.wrongEmail; // but here index start from 0. its the place. 0,1,2,3,4
		data[0][1] = dt.wrongPassword;
		data[0][2] = dt.globalError;
		// 1st row
		data[1][0] = " ";
		data[1][1] = dt.wrongPassword;
		data[1][2] = dt.uNameError;
		
		data[2][0]=dt.wrongEmail;
		data[2][1]=" ";
		data[2][2]=dt.pError;
		
		data[3][0]=dt.emailspecial;
		data[3][1]=dt.wrongPassword;
		data[3][2]=dt.sPCharError;

		return data;

	}

}
