package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage {
	WebDriver driver;
	
	/*WebElement email=driver.findElement(By.id("usernameInput-input"));
	WebElement password=driver.findElement(By.id("password-input"));
	WebElement loginButton=driver.findElement(By.id("signIn"));
	WebElement globalError=driver.findElement(By.className("_25yKbJzdQG5JaS-QB9EOCt"));
	WebElement error=driver.findElement(By.cssSelector("div[class='Error__text'] span"));*/
	//driver is not initialized now, so it will create error. null pointer error 
	
	@FindBy(id="usernameInput-input")  //to initialize to the variable. not find it now, becouse the webpage is not loaded.
	public WebElement email;            //when we make object of class LoginPage, all the global variables are initialized first
	                                     //use FindBy, won't find the webelement now, only initialize it.
	@FindBy(id="password-input")
	public WebElement password;
	
	@FindBy(id="signIn")
	public WebElement loginButton;
	
	@FindBy(className="_25yKbJzdQG5JaS-QB9EOCt")
	public WebElement globalError;
	
	@FindBy(css="div[class='Error__text'] span")
	public WebElement error;

	public void openBrowser() throws IOException {
		FileInputStream f=new FileInputStream("C:\\Selenium\\Nikul Patel\\Testing\\Property_File.properties");
		Properties prop=new Properties();

		prop.load(f);

		String browser = prop.getProperty("browser");
		System.out.println(browser);
		if(browser.contentEquals("edge"))
		{
		System.setProperty("webdriver.edge.driver", "C:\\Selenium\\Udemy\\edgedriver_win64 (1)\\msedgedriver.exe");
		driver = new EdgeDriver();
		}
		else {
			System.setProperty("webdriver.chrome.driver","C:\\Selenium\\Nikul Patel\\chromedriver.exe" );
		 driver = new ChromeDriver();
		}
		PageFactory.initElements(driver, this);
	}

	public void loginPage() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(
				"https://auth.scotiaonline.scotiabank.com/online?oauth_key=0pL1eJB4lzE&oauth_key_signature=eyJraWQiOiJrUFVqdlNhT25GWUVDakpjMmV1MXJvNGxnb2VFeXJJb2tCbU1oX3BiZXNVIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJyZWZlcmVyIjoiaHR0cHM6XC9cL3d3dy5zY290aWFiYW5rLmNvbVwvIiwib2F1dGhfa2V5IjoiMHBMMWVKQjRsekUiLCJjb25zZW50X3JlcXVpcmVkIjpmYWxzZSwicmVkaXJlY3RfdXJpIjoiaHR0cHM6XC9cL3d3dy5zY290aWFvbmxpbmUuc2NvdGlhYmFuay5jb21cL29ubGluZVwvbGFuZGluZ1wvb2F1dGhsYW5kaW5nLmJucyIsImV4cCI6MTY0Mjk3MTk3MywiaWF0IjoxNjQyOTcwNzczLCJqdGkiOiIwOWZkM2Q3ZS01ZGJhLTRhNzMtYTkzMC1mMWZiMjJlM2Q3N2EiLCJjbGllbnRfaWQiOiI4ZWU5MGMzOS0xYzUyLTRmZjQtOGFlNi1hN2I1NGM1Mzk5MzMiLCJjbGllbnRfbWV0YWRhdGEiOnsiQ2hhbm5lbElEIjoiU09MIiwiQXBwbGljYXRpb25Db2RlIjoiSDcifSwiaXNzdWVyIjoiaHR0cHM6XC9cL3Bhc3Nwb3J0LnNjb3RpYWJhbmsuY29tIn0.Zi4cXEg2TToEHgBV2MrWBqHPLNzVGSKi1kk8A3kI4FgDKpn8f3gpKsVgOsag7M1PXtn5bH_aTsUjTA46jG67sptAqHySLZhFwa--Sr9yvLEUkG1KoS4eZ__sBrZdcsRlLjQ87Z6rV9YOjZihxHqwcUYy5xObZZQFArsUBgs0Kt6U-WtCJAA2dSjLK8IojDu8ENBl-H5Oydp2L2Nnf4DPY-ceMxwiqRN6EyruWDoh2ES2JrFxlgZiwag_bSYuO1rgvM67zC-IHQ6KVf-g-InRlNQKu8Ce1tyvsXv_f9sTmpCC6TLlpIHFL6eNIzlttLfMA0iY0oKWIFV-RsHJfzd1XQ&preferred_environment=");
	

	}

	public void closeBrowser() {
		driver.quit();
	}

	public void login(String uName, String pWord) throws InterruptedException {
		email.clear();
		email.sendKeys(uName);
		password.clear();
		password.sendKeys(pWord);
		loginButton.click();
		Thread.sleep(2000);

	}

	public String genError() {
		String actual = globalError.getText();
		System.out.println(actual);
		return actual;
	}

	public String readError() {
		String actual =error.getText();
		System.out.println(actual);
		return actual;

	}
}
