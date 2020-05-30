package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login_Page {

	public WebDriver driver;

	By Account_ID = By.id("resolving_input");
	By UserName = By.id("username");
	By Password = By.id("password");
	By Existing_User = By.className("sign-in-link");
	By IAM_User = By.id("iam_user_radio_button");
	By Next = By.id("next_button");
	By SignIn = By.id("signin_button");

	public Login_Page(WebDriver driver) {
		// TODO Auto-generated constructor stub

		this.driver = driver;
	}

	public WebElement AccountID() {

		return driver.findElement(Account_ID);
	}

	public WebElement Username() {

		return driver.findElement(UserName);
	}

	public WebElement Password() {

		return driver.findElement(Password);
	}

	public WebElement ExistingUser() {

		return driver.findElement(Existing_User);
	}

	public WebElement IAM_User() {
		return driver.findElement(IAM_User);

	}

	public WebElement Next_button() {
		return driver.findElement(Next);
	}

	public WebElement SignIn() {
		return driver.findElement(SignIn);
	}
}
