package Base;

import PageObjectsTricentis.TricentisEnterInsurantVehicleDataPage;
import PageObjectsTricentis.TricentisEnterVehicleDataPage;
import PageObjectsTricentis.TricentisHomePage;
import Utilities.JsonUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import PageObjectsSample.StagingLandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest extends JsonUtils {
    public static WebDriver driver;
    public Properties prop;
    public TricentisHomePage TricentisHomeP;
    public TricentisEnterVehicleDataPage TricentisEnterVehicleDP;
    public TricentisEnterInsurantVehicleDataPage TricentisEnterInsurantDP;


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
        else if(browserName.equalsIgnoreCase("firefox")){

        }
        else if(browserName.equalsIgnoreCase("edge")){
            System.setProperty("webdriver.edge.driver","edge.exe");
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        return driver;
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException{
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
    }

    @BeforeMethod
    public void launchapplication() throws IOException, InterruptedException {
        prop = new Properties();
        driver = InitializeDriver();
        TricentisHomeP = new TricentisHomePage(driver);
        TricentisEnterVehicleDP = new TricentisEnterVehicleDataPage(driver);
        TricentisEnterInsurantDP = new TricentisEnterInsurantVehicleDataPage(driver);

        //*******************************************************************
        driver.get(prop.getProperty("URLTricentis"));

    }
    @AfterMethod
    public void CloseBrowser(){
        driver.manage().deleteAllCookies();
        driver.close();
    }


}
