package TestCases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObject.AWS_Dashboard;
import Resources.base;

public class Route_53 extends base {
	public static Logger log = LogManager.getLogger(base.class.getName());

	public static WebDriver driver;

	@BeforeTest
	public void Route53_merge() throws InterruptedException, IOException {

		driver = login();
	}
	@Test
	public void route53_create() throws InterruptedException {

		String Domain_Name = prop.getProperty("DOMAINNAME");
		String CNAME = prop.getProperty("CNAME");
		String Fetched_Domain = "";
		AWS_Dashboard Dropdown = new AWS_Dashboard(driver);

		WebElement Cloudfront = driver.findElement(By.xpath("//*[@id='awsc-services-container']/ul[1]/li[36]/a/span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement scroll = Cloudfront;
		js.executeScript("arguments[0].scrollIntoView(true)", scroll);

		Cloudfront.click();

		/*
		 * Finding Unique Domain_Name of the newly created cloud front distribution for
		 * Route53 Creation
		 */
		WebElement Table = driver.findElement(By.xpath("//table[@class='GHRMFH5CMAE']"));
		int count_row = Table.findElements(By.xpath("//table[@class='GHRMFH5CMAE']/tbody/tr")).size();
		System.out.println(count_row);
		int count_coloumns = Table.findElements(By.xpath("//table[@class='GHRMFH5CMAE']/tbody/tr/td[7]")).size();
		for (int i = 0; i < count_coloumns; i++) {
			String domainName = Table.findElements(By.xpath("//table[@class='GHRMFH5CMAE']/tbody/tr/td[7]")).get(i)
					.getText();

			if (domainName.equalsIgnoreCase(CNAME)) {
				Fetched_Domain = Table.findElements(By.xpath("//table[@class='GHRMFH5CMAE']/tbody/tr/td[4]")).get(i)
						.getText();
			}
			System.out.println(Fetched_Domain);
		}

		Dropdown.Service_Dropdown().click();
		WebElement Route53_Select = driver
				.findElement(By.xpath("//*[@id='awsc-services-container']/ul[1]/li[37]/a/span"));
		Route53_Select.click();

		WebElement Hosted_Zones = driver.findElement(By.xpath("//*[contains(text(),'Hosted zones')]"));
		Hosted_Zones.click();

		WebElement domain_Name_Click = driver.findElement(By.linkText(Domain_Name));
		domain_Name_Click.click();
		WebElement Create_RecordSet = driver.findElement(By.xpath("//div[@class='GLATOE0HNE']/button[2]"));
		Create_RecordSet.click();

		WebElement recordSetName = driver.findElement(By.xpath("//input[@class='GLATOE0P1D']"));
		recordSetName.click();
		String parts[] = CNAME.split("\\.");
		String recordName = parts[0];
		recordSetName.sendKeys(recordName);
		WebElement Alias_Yes = driver.findElement(By.xpath("//input[@name='aliased']"));
		Alias_Yes.click();

		WebElement AliasName = driver
				.findElement(By.xpath("//input[@class='gwt-SuggestBox GLATOE0EFE GLATOE0BHE GLATOE0PGE GLATOE0IFE']"));
		AliasName.click();
		AliasName.sendKeys(Fetched_Domain);
		WebElement FinalCreate = driver
				.findElement(By.xpath("//button[@class='GLATOE0FE GLATOE0D GLATOE0B GLATOE0L GLATOE0OGE']"));
		FinalCreate.click();
	}

	@AfterTest
	public void teardown() {
		driver.close();
	}

}
