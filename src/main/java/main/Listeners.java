package main;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Resources.base;

public class Listeners extends base implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReport.extentReportDemo();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.PASS, "Successful");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		WebDriver driver = null;
		extentTest.get().fail(result.getThrowable());
		Object testObject = result.getInstance();
		Class realClass = result.getTestClass().getRealClass();
	
			try {
				driver = (WebDriver) realClass.getDeclaredField("driver").get(testObject);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
		try {
			extentTest.get().addScreenCaptureFromPath(getScreenShotPath(driver, result.getMethod().getMethodName()),
					result.getMethod().getMethodName());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

		extent.flush();

	}

}
