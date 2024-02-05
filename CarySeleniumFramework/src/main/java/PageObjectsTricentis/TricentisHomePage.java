package PageObjectsTricentis;

import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class TricentisHomePage extends AbstractComponent {

    WebDriver driver;
    public TricentisHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nav_automobile")
    WebElement AutomobileHeaderBtn;
    @FindBy(id = "nav_truck")
    WebElement TruckHeaderBtn;
    @FindBy(id = "nav_motorcycle")
    WebElement MotorcycleHeaderBtn;
    @FindBy(id = "nav_camper")
    WebElement CamperHeaderBtn;
    @FindBy(id="selectedinsurance")
    WebElement SelectedInsuranceHeaderTxt;

    //*************************************************************************************

    @Step("Step: Click The Vehicle Header Button In Tricentis Home Page")
    @Severity(SeverityLevel.MINOR)
    public void ClickVehicleHeaderBtn(String Vehicle) throws InterruptedException {
        if(Vehicle.equalsIgnoreCase("Automobile")) {
            Allure.step("Click Automobile Header Button.");
            Assert.assertTrue(AutomobileHeaderBtn.isEnabled(), "Automobile Header Button Is Disabled.");
            AutomobileHeaderBtn.click();
        }else if(Vehicle.equalsIgnoreCase("Truck")){
            Allure.step("Click Truck Header Button.");
            Assert.assertTrue(TruckHeaderBtn.isEnabled(), "Truck Header Button Is Disabled.");
            TruckHeaderBtn.click();
        }else if (Vehicle.equalsIgnoreCase("Motorcycle")) {
            Allure.step("Click Motorcycle Header Button.");
            Assert.assertTrue(MotorcycleHeaderBtn.isEnabled(), "Motorcycle Header Button Is Disabled.");
            MotorcycleHeaderBtn.click();
        }else if (Vehicle.equalsIgnoreCase("Camper")) {
            Allure.step("Click Camper Header Button.");
            Assert.assertTrue(CamperHeaderBtn.isEnabled(), "Camper Header Button Is Disabled.");
            CamperHeaderBtn.click();
        }


        try {
            String SelectedVehicleInsuranceActualTxt = SelectedInsuranceHeaderTxt.getText();
            String SelectedVehicleInsuranceExpectedTxt = Vehicle+" Insurance";
            Assert.assertEquals(SelectedVehicleInsuranceActualTxt,SelectedVehicleInsuranceExpectedTxt, "Automobile Button Is Not Selected! Since The Expected Header Txt Doesn't Show");
            AbstractComponent.screenshot(driver);
            Allure.addAttachment("Vehicle Button Is Selected, Check For Drop Down Values", "Actual Values: " + SelectedVehicleInsuranceActualTxt);
        } catch (AssertionError e) {
            AbstractComponent.screenshot(driver);
            System.out.println("Assertion failed: " + e.getMessage());
            Allure.addAttachment("Assertion Failure Details", e.getMessage());
            throw e;

        }
    }

}
