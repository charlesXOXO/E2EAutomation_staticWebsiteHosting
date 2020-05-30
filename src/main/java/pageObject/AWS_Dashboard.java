package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AWS_Dashboard {

	public WebDriver driver;
	By Service_Dropdown = By.className("nav-elt-label");

	public AWS_Dashboard(WebDriver driver) {
		// TODO Auto-generated constructor stub

		this.driver = driver;

	}
	public WebElement Service_Dropdown() {

		return driver.findElement(Service_Dropdown);
	}

}
