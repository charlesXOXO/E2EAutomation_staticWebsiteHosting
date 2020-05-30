package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Landing_Page {
	
	
	public Landing_Page(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		this.driver = driver;
	}
	
	public WebDriver driver;
	
	
	By signin = By.className("lb-btn-p-primary");
	
	public  WebElement get_login()
	{
		return driver.findElement(signin);
	}
	

}
