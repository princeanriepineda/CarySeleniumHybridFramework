package Base;

import PageObjectsDataPulseAdmin.DataPulseUserAdminLogInPage;
import PageObjectsTricentis.TricentisEnterInsurantVehicleDataPage;
import PageObjectsTricentis.TricentisEnterVehicleDataPage;
import PageObjectsTricentis.TricentisHomePage;
import Resources.ExtentReporterNG;
import Utilities.JsonUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest extends JsonUtils {
    public static WebDriver driver;
    public Properties prop;
    public ExtentReports extent;
    public TricentisHomePage TricentisHomeP;
    public TricentisEnterVehicleDataPage TricentisEnterVehicleDP;
    public TricentisEnterInsurantVehicleDataPage TricentisEnterInsurantDP;
    public DataPulseUserAdminLogInPage DPUALoginP;


    public WebDriver InitializeDriver() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Resources/Global.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");
        String chromeDriverVersion = "120.0.6099.129";

        if(browserName.equalsIgnoreCase("chrome")) {
           WebDriverManager.chromedriver().browserVersion(chromeDriverVersion).setup();
           driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("firefox")){}
        else if(browserName.equalsIgnoreCase("edge")){
            System.setProperty("webdriver.edge.driver","edge.exe");
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        return driver;
    }


    @BeforeMethod
    public void launchapplication() throws IOException, InterruptedException {
        prop = new Properties();
        driver = InitializeDriver();
        extent = ExtentReporterNG.getReporterObject();

        //******************************************************************
        TricentisHomeP = new TricentisHomePage(driver);
        TricentisEnterVehicleDP = new TricentisEnterVehicleDataPage(driver);
        TricentisEnterInsurantDP = new TricentisEnterInsurantVehicleDataPage(driver);
        //*******************************************************************
        DPUALoginP = new DataPulseUserAdminLogInPage(driver);
        //*******************************************************************
        driver.get(prop.getProperty("URLTricentis"));

    }
    @AfterMethod
    public void CloseBrowser(){
        driver.manage().deleteAllCookies();
        driver.close();
        extent.flush();
    }


}
