package TestCases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

public class S3CloudFront extends base {
	
	public static Logger log = LogManager.getLogger(base.class.getName());
	public static WebDriver driver;

	@BeforeTest
	public void prerequisites() throws IOException, InterruptedException {

		/* &&&&&&&&&&&&&&&&& INTIALIZING BROWSER &&&&&&&&&&&&& */
		driver=login();
	}

	@Test
	public void S3Bucket_Creation() throws InterruptedException, IOException {
		
			// TODO Auto-generated method stub

			String S3_bucket_Name = prop.getProperty("s3bucketname");
			String bucket_name = S3_bucket_Name.toLowerCase();
			String HTML = prop.getProperty("HTML_FILE");

			WebDriverWait w = new WebDriverWait(driver, 5);

			WebElement S3_Dropdown = driver.findElement(By.xpath("//*[@id='awsc-services-container']/ul[1]/li[11]/a/span"));
			S3_Dropdown.click();

			boolean newConsolePresent = driver.findElements(By.xpath("//awsui-alert[@fac-id='s3-polaris-opt-in']"))
					.size() > 0;

			if (newConsolePresent) {

				driver.findElement(By.xpath("//polaris-opt-in-link[@class='ng-scope ng-isolate-scope']")).click();

			}
			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Create bucket']")));
			WebElement Create_Bucket = driver.findElement(By.xpath("//span[text()='Create bucket']"));
			Create_Bucket.click();
			WebElement Bucket_Name = driver.findElement(By.id("awsui-input-1"));
			Bucket_Name.sendKeys(bucket_name);

			/* &&&&&&&&&&&&&&&&& AUTO-SUGGESTIVE DROPDOWN &&&&&&&&&&&&& */

			driver.findElement(By.xpath("//awsui-icon[@class='awsui-select-trigger-icon']")).click();
			driver.findElement(By.xpath("//*[@id='awsui-input-2']")).sendKeys("US EAST");
			System.out.println(driver.findElement(By.xpath("//*[@id='awsui-input-2']")).getAttribute("value"));

			driver.findElement(By.id("awsui-input-2")).sendKeys(Keys.DOWN);
			driver.findElement(By.id("awsui-input-2")).sendKeys(Keys.ENTER);

			// Using JavaScript executor to scroll down to "view" the next WebElement in
			// question
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			WebElement Bucket_Creation = driver.findElement(By.className("awsui-checkbox-native-input"));
			js1.executeScript("arguments[0].scrollIntoView(true)", Bucket_Creation);
			Bucket_Creation.click();

			WebElement scroll2 = driver.findElement(By.xpath("//awsui-button[@id='createBucket']"));
			js1.executeScript("arguments[0].scrollIntoView(true)", scroll2);

			// Streamlining the driver to a particular element (can be used when there are
			// multiple results for the same xpath)
			WebElement Focus = driver.findElement(By.className("create-bucket-bpa__warning"));
			Focus.findElement(By.className("awsui-checkbox-label")).click();
			driver.findElement(By.xpath("//awsui-button[@id='createBucket']")).click();

			/////////////////////////////////////
			driver.findElement(By.linkText(bucket_name)).click();
			driver.findElement(By.xpath(
					"//*[@id='sidebarNavDiv']/div[2]/div[1]/awsui-tabs/div/div/div/span/div/ng-include/div[1]/div/div/awsui-button[1]/button/span[2]"))
					.click();

			driver.findElement(By.id("uploadInputNoFilesSelected")).click();
			/*
			 * &&&&&&&&&&&&&&&&& MULTIPLE FILE UPLOAD USING THIRD PARTY TOOL(AUTOIT)
			 * &&&&&&&&&&&&&
			 */
			log.info("AUTOIT INTIALIZED FOR FILE UPLOAD");
			Thread.sleep(2000L);
			Runtime.getRuntime().exec("D:\\GetallFiles.exe");
			Thread.sleep(2000L);
			driver.findElement(By.xpath("//awsui-button[@id = 'next']")).click();
			driver.findElement(By.xpath("//div[@id = 'dropdown']")).click();
			driver.findElement(By.xpath("//div[@id = 'dropdown']/div/div[2]")).click();
			driver.findElement(By.xpath("//awsui-button[@id = 'next']")).click();
			driver.findElement(By.xpath("//awsui-button[@id = 'next']")).click();
			driver.findElement(By.xpath("//awsui-button[@click='uploadNow()']")).click();

			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Permissions']")));
			WebElement Permissions = driver.findElement(By.xpath("//a[text()='Permissions']"));
			Permissions.click();
			w.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//awsui-button[@id='permission-policy-button']/button")));
			WebElement Bucket_Policy = driver
					.findElement(By.xpath("//awsui-button[@id='permission-policy-button']/button"));
			Bucket_Policy.click();

			log.info("BUCKET POLICY CONFIGURATION");
			/*
			 * FUNCTION : Making use of a policy to make bucket access public, similar
			 * bucket policy can be found at:
			 * https://docs.aws.amazon.com/AmazonS3/latest/dev/access-policy-language-
			 * overview.html* Logic Employed: Copying the policy in a text file in the local
			 * machine reading it copying it to a string and passing as an input via send
			 * keys
			 */
			File inFile = new File("C:\\Users\\charl\\Desktop\\Script.txt");
			StringBuilder targetString = new StringBuilder("");
			try {
				FileReader fr = new FileReader(inFile);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String s = null;
				while ((s = br.readLine()) != null) {
					targetString.append(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			WebElement Bucket_Intital_Click = driver.findElement(By.xpath("//div[@id='policy-editor']"));
			Bucket_Intital_Click.click();

			WebElement Bucket_Policy_Text = driver.findElement(By.xpath("//textarea[@class='ace_text-input']"));
			Bucket_Policy_Text.sendKeys(targetString);

			WebElement Save_Code = driver.findElement(By.xpath("//awsui-button[@id='save-code']/button"));
			Save_Code.click();

			/* &&&&&&&&&&&&&&&&& STATIC WEBSITE HOSTING &&&&&&&&&&&&& */

			log.info("STATIC WEBSITE HOSTING INTIALIZED");

			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='awsui-tabs-container']/li[2]/a")));
			driver.findElement(By.xpath("//ul[@class='awsui-tabs-container']/li[2]/a")).click();
			driver.findElement(By.xpath("//bucket-website[@id='bucket-website-card']/card/div")).click();

			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//awsui-radio-button[@value='useBucket']")));
			driver.findElement(By.xpath("//awsui-radio-button[@value='useBucket']")).click();

			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='index.html']")));
			driver.findElement(By.xpath("//input[@placeholder='index.html']")).click();
			driver.findElement(By.xpath("//input[@placeholder='index.html']")).sendKeys(HTML);

			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			WebElement scroll_save = driver.findElement(By.xpath("//div[@class='ng-scope']/div/div/awsui-button[2]"));
			////////////////////////
			js2.executeScript("arguments[0].scrollIntoView(true)", scroll_save);

			driver.findElement(By.xpath("//div[@class='ng-scope']/div/div/awsui-button[2]")).click();
			driver.findElement(By.xpath("//bucket-website[@id='bucket-website-card']/card/div")).click();

			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='ng-binding']")));

			String S3URL = driver.findElement(By.xpath("//a[@class='ng-binding']")).getText();

			String afterSplitURL[] = S3URL.split("http://");

			for (int i = 0; i < afterSplitURL.length; i++) {

				System.out.println(afterSplitURL[i]);
			}
			driver.findElement(By.xpath("//bucket-website[@id='bucket-website-card']/card/div")).click();
			driver.findElement(By.xpath("//a[@class='ng-binding']")).click();

			log.info("STATIC WEBSITE HOSTING SUCCESSFUL");
	}

	@AfterTest
	public void teardown() {
		driver.close();
	}

}
