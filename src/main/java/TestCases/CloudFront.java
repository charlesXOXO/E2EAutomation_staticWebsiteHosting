package TestCases;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import Resources.base;

public class CloudFront extends base {

	public static Logger log = LogManager.getLogger(base.class.getName());
	public static WebDriver driver;

	@BeforeTest
	public void prerequisites() throws IOException, InterruptedException {
		driver = login();
	}

	@Test
	public void CloudFront_Distirbution() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		String S3_bucket_Name = prop.getProperty("s3bucketname");
		String CNAME = prop.getProperty("CNAME");
		String HTML = prop.getProperty("HTML_FILE");
		WebDriverWait w = new WebDriverWait(driver, 5);
		WebElement Cloudfront = driver.findElement(By.xpath("//*[@id='awsc-services-container']/ul[1]/li[36]/a/span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement scroll = Cloudfront;
		js.executeScript("arguments[0].scrollIntoView(true)", scroll);
		w.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='awsc-services-container']/ul[1]/li[36]/a/span")));
		Cloudfront.click();
		/*
		 * The "CREATE DISTRIBUTION WEBELEMENT" was dynamic if multiple records the id
		 * tag was different as compared to no records
		 */
		Boolean isPresent = driver.findElements(By.xpath("//table[@class='GHRMFH5CMAE']")).size() > 0;
		System.out.println(isPresent);
		WebElement Create_Dist;
		if (isPresent.toString().equalsIgnoreCase("false")) {
			Create_Dist = driver.findElement(By.xpath("//button[@id='gwt-debug-getStartedCreateDistributionBtn']"));
			Create_Dist.click();

		} else {

			Create_Dist = driver.findElement(By.xpath("//button[@id='gwt-debug-createDistributionBtn']"));
			Create_Dist.click();

		}

		WebElement Get_Started = driver.findElement(By.xpath("//button[@id='gwt-debug-createWebDistr']"));
		Get_Started.click();
		WebElement Origin_Domain_Name = driver.findElement(By.xpath("//input[@class='gwt-SuggestBox GHRMFH5CHN']"));
		Origin_Domain_Name.sendKeys(S3_bucket_Name.toLowerCase());
		Origin_Domain_Name.sendKeys(Keys.ARROW_DOWN);
		Origin_Domain_Name.sendKeys(Keys.ENTER);

		/* DEFAULT CACHE BEHAVIOR SETTINGS */
		WebElement Protocol_Policy = driver.findElement(By.xpath("//*[contains(text(),'Redirect HTTP to HTTPS')]"));
		Protocol_Policy.click();

		WebElement CNAMES = driver
				.findElement(By.xpath("//*[@id='gwt-debug-webCNames']/tbody/tr/td[1]/div/div[2]/div[1]/textarea"));

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		/////////////////////////
		js1.executeScript("arguments[0].scrollIntoView(true)", CNAMES);
		CNAMES.sendKeys(CNAME);

		WebElement Custom_SSL = driver.findElement(By.xpath("//input[@id='gwt-debug-webCertCustom-input']"));
		Custom_SSL.click();

		WebElement Custom_SSL_Suggest_box = driver.findElement(
				By.xpath("//*[@id='gwt-debug-webSSLCombo']/tbody/tr/td[1]/div/div[2]/div[1]/div/div[1]/input"));
		Custom_SSL_Suggest_box.click();
		Custom_SSL_Suggest_box.sendKeys(Keys.ARROW_DOWN);
		Custom_SSL_Suggest_box.sendKeys(Keys.ARROW_DOWN);
		Custom_SSL_Suggest_box.sendKeys(Keys.ENTER);

		WebElement Default_Root_Object = driver.findElement(By.xpath("//input[@tabindex='232']"));
		js1.executeScript("arguments[0].scrollIntoView(true)", Default_Root_Object);
		Default_Root_Object.sendKeys(HTML);

		WebElement Create_Distribution = driver
				.findElement(By.xpath("//button[@id='gwt-debug-saveDistributionWizBtn']"));
		Create_Distribution.click();
		WebElement check_Distribution = driver
				.findElement(By.xpath("//*[@id='aws-console-rootlayout']/div[2]/div/div[2]/div/div[2]/div/div[2]/div"));
		check_Distribution.click();

	}

	@AfterTest
	public void teardown() {

	}
}
