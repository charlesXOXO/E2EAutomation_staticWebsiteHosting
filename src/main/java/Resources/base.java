package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import pageObject.AWS_Dashboard;
import pageObject.Landing_Page;
import pageObject.Login_Page;


public class base {

	public static WebDriver driver;
	public static Properties prop;

	public static WebDriver intializeDriver() throws IOException 
	{
		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"D:\\NewWorkspace\\Script\\src\\main\\java\\Resources\\Data.properties");
		prop.load(fis);
		String browser_name = prop.getProperty("browser");
		if (browser_name.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if (browser_name.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			driver = new FirefoxDriver();

		}
		else if (browser_name.equalsIgnoreCase("I.E")){
			System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}
	
	public String getScreenShotPath(WebDriver driver,String testCaseName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destPath = System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
		File file = new File(destPath);
		FileUtils.copyFile(source, file);
		return destPath;
	}

	public WebDriver login() throws IOException
	{
		driver = intializeDriver();

		Landing_Page Landing = new Landing_Page(driver);
		Login_Page Login = new Login_Page(driver);
		AWS_Dashboard Dropdown = new AWS_Dashboard(driver);

		String Username = prop.getProperty("Username");
		String Password = prop.getProperty("Password");
		String Account_ID = prop.getProperty("Account_ID");
		String URL = prop.getProperty("URL");

		driver.get(URL);
		Landing.get_login().click();

		Login.ExistingUser().click();
		Login.IAM_User().click();
		Login.AccountID().sendKeys(Account_ID);
		Login.Next_button().click();

		// Enter the credentials to your IAM ROLE assinged by AWS Administrator

		Login.Username().sendKeys(Username);
		Login.Password().sendKeys(Password);
		Login.SignIn().click();
		Dropdown.Service_Dropdown().click();
		
		return driver;
	}
}
