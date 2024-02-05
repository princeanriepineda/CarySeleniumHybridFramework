package Base;

import Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;


public class Listeners extends BaseTest implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReporterObject();
    ThreadLocal <ExtentTest> extentTest =  new ThreadLocal();
    ThreadLocal <Integer> retryAttempts = new ThreadLocal<>();

    private static ThreadLocal<String> actualValueThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> expectedValueThreadLocal = new ThreadLocal<>();

    public static void setActualAndExpectedValues(String actualValue, String expectedValue) {
        actualValueThreadLocal.set(actualValue);
        expectedValueThreadLocal.set(expectedValue);
    }


    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //test.log(Status.PASS,"TestCase Name: "+result.getName());
        extentTest.get().log(Status.PASS, "Test Passed: "+result.getName());
        extentTest.get().pass("Assertion Passed");


    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL,"Test Failed");
        //extentTest.get().fail(result.getThrowable());

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e1) {
            //throw new RuntimeException(e);
            e1.printStackTrace();
        }


        String filePath =  null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());

        saveScreenshotOnFailure(BaseTest.driver);
        saveLogs(result.getMethod().getConstructorOrMethod().getName());




    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, result.getThrowable()).skip("***********************  SKIPPED Testcase: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        int attempts = retryAttempts.get();
        if (attempts > 0) {
            test.log(Status.WARNING, "Retry attempt: " + attempts);
            test.log(Status.INFO, "Test Case Retry");
            test.log(Status.WARNING, "Check Code When Done");
            extentTest.get().log(Status.WARNING, "Retry Step: " + result.getName());
            extentTest.get().warning(result.getThrowable());
        }
        retryAttempts.set(attempts + 1);


    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshotOnFailure(WebDriver driver){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
    @Attachment(value = "Stacktrace", type = "text/plain")
    public static String saveLogs(String messege){
        return messege;
    }




}
