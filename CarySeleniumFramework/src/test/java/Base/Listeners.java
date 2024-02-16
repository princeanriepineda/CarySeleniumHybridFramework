package Base;

import Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Listeners extends BaseTest implements ITestListener {


    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReporterObject();
    static ThreadLocal <ExtentTest> extentTest =  new ThreadLocal();
    ThreadLocal <Integer> retryAttempts = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result)  {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getName());
        extentTest.get().pass("Assertion Passed");

        try {
            Thread.sleep(2000);
            // Capture Screenshot
            captureScreenshot(result.getMethod().getMethodName());
            saveLogs(result.getMethod().getConstructorOrMethod().getName() + " - Test Passed");
            System.out.println("Test Passed: " + result.getMethod().getMethodName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().fail(result.getThrowable());

        // Capture Screenshot
        captureScreenshot(result.getMethod().getMethodName());

        // Log additional information
        extentTest.get().log(Status.FAIL, "Test Data: " + getDataForFailedTest(result));
        saveLogs(result.getMethod().getConstructorOrMethod().getName() + " - Test Failed");
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
    }



    private String getDataForFailedTest(ITestResult result) {
        // Implement logic to retrieve test data or relevant context for the failed test
        return "Test data or context information";
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, result.getThrowable()).skip("SKIPPED Testcase: " + result.getName());
        saveLogs(result.getMethod().getConstructorOrMethod().getName() + " - Test Skipped");
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        int attempts = retryAttempts.get();
        if (attempts > 0) {
            // Log retry attempts
            extentTest.get().log(Status.WARNING, "Retry attempt: " + attempts);
            extentTest.get().log(Status.INFO, "Test Case Retry");
            extentTest.get().log(Status.WARNING, "Check Code When Done");
            extentTest.get().log(Status.WARNING, "Retry Step: " + result.getName());
            extentTest.get().warning(result.getThrowable());
        }
        retryAttempts.set(attempts + 1);
        saveLogs(result.getMethod().getConstructorOrMethod().getName() + " - Test Failed (within success percentage)");
        System.out.println("Test Failed (within success percentage): " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        // Initialize Extent Reports (if not already initialized)
        if (extent == null) {
            extent = ExtentReporterNG.getReporterObject();
        }
        System.out.println("Test Suite Started: " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush Extent Reports
        extent.flush();
        System.out.println("Test Suite Finished: " + context.getSuite().getName());
    }


    //*******************************************************************************************************
    @Attachment(value = "Stacktrace", type = "text/plain")
    public static String saveLogs(String messege){
        return messege;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] captureScreenshotAndSaveToExtentReport(String methodName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

        try {
            // Include timestamp in the screenshot file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = System.getProperty("user.dir") + "/reports/" + methodName + "_" + timestamp + "_failed.png";

            // Save the screenshot to a file (optional)
            FileUtils.writeByteArrayToFile(new File(screenshotPath), screenshot);

            // Attach the screenshot to the Extent Report
            extentTest.get().addScreenCaptureFromPath(screenshotPath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshot;
    }



    public void captureScreenshot(String methodName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshot = ts.getScreenshotAs(OutputType.FILE);

        try {
            // Include timestamp in the screenshot file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = System.getProperty("user.dir") +"/reports/" + methodName + "_" + timestamp + "_failed.png";

            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot captured: " + screenshotPath);

            File screenshotFile = new File(screenshotPath);
            if (screenshotFile.exists()) {
                extentTest.get().addScreenCaptureFromPath(screenshotPath);
            } else {
                System.out.println("Screenshot file not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
