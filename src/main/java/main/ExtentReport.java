package main;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport  {
	
	static ExtentReports extent;
	public static ExtentReports extentReportDemo() {
		String source = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(source);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Automation Results");
		extent = new ExtentReports();	
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Charles Chacko");
		return extent;
	}
}
