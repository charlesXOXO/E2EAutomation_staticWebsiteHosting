package TestCases;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;

import Resources.base;
public class Final_Browser extends base{
	public static Logger log = LogManager.getLogger(base.class.getName());	

	@Test
	public void finalBrowserTesting() throws IOException {
		driver = intializeDriver();
		String CNAME = prop.getProperty("FinalURL");
		driver.get(CNAME);
	}
}
